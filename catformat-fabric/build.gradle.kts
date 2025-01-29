plugins {
    id("fabric-loom") version "1.6-SNAPSHOT"
    id("maven-publish")
}

val version: String by project
val group: String by project
val archiveBaseName: String by project

val yarnMappings: String by project
val minecraftVersion: String by project
val loaderVersion: String by project

base {
    archivesName.set(archiveBaseName)
}

repositories {
}

dependencies {
    minecraft("com.mojang:minecraft:${minecraftVersion}")
    mappings("net.fabricmc:yarn:${yarnMappings}:v2")
    modImplementation("net.fabricmc:fabric-loader:${loaderVersion}")
    project(":catformat-core").let {
        implementation(it)
        include(it)
    }
}

tasks.processResources {
    inputs.property("version", version)
    inputs.property("minecraft_version", minecraftVersion)
    inputs.property("loader_version", loaderVersion)

    filesMatching("fabric.mod.json") {
        expand(
            "version" to version,
            "minecraft_version" to minecraftVersion,
            "loader_version" to loaderVersion
        )
    }
}

val targetJavaVersion = 21
tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
        options.release.set(targetJavaVersion)
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
    }
    withSourcesJar()
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_${archiveBaseName}" }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = archiveBaseName
            from(components["java"])
        }
    }

    repositories { }
}