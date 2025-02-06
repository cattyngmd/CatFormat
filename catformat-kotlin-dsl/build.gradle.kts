plugins {
    kotlin("jvm") version "1.9.23"
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":catformat-core"))
}

kotlin {
    jvmToolchain(21)
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(21)
}