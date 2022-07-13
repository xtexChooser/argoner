pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "argoner"

include(":common")
include(":server")
include(":frontend")
include(":client")
include("protocol")
include("common")
