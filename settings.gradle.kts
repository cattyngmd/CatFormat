pluginManagement {
    repositories {
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        gradlePluginPortal()
    }
}

include("catformat-legacy-text")
include("catformat-fabric")
include("catformat-core")
include("catformat-kotlin-dsl")
