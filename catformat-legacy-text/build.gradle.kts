import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":catformat-core"))
}

tasks {
    build {
        dependsOn("shadowJar")
    }

    named<ShadowJar>("shadowJar") {
        archiveClassifier.set("")
    }
}

