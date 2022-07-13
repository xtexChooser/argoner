plugins {
    kotlin("multiplatform")
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
                api("io.github.microutils:kotlin-logging:2.1.23")
                api("org.jetbrains.kotlinx:kotlinx-serialization-core:1.3.3")
            }
        }
    }
}
