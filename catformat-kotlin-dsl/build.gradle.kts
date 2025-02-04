plugins {
    kotlin("jvm") version "1.9.23"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":catformat-core"))
}

kotlin {
    jvmToolchain(17)
}