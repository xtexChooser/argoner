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
}
