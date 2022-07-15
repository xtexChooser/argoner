plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

kotlin {
    jvm {
    }
    js(IR) {
        browser { }
        nodejs { }
        binaries.library()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(kotlin("reflect"))
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
                api("io.github.microutils:kotlin-logging:2.1.23")
                api("app.softwork:kotlinx-uuid-core:0.0.16-sqldelight2a03")
                api("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
            }
        }
    }
}
