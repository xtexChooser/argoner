plugins {
    kotlin("multiplatform")
}

kotlin {
    js(IR) {
        browser { }
        nodejs { }
        binaries.library()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":common"))
            }
        }
    }
}
