allprojects {
    group = "dev.cattyn"
    version = project.version

    repositories {
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    configure<PublishingExtension> {
        publications {
            create<MavenPublication>("maven") {
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()
                from(components["java"])
            }
        }
    }
}