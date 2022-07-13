plugins {
    kotlin("multiplatform")
}

kotlin {
    js("common", IR) {
        browser { }
        nodejs { }
        binaries.library()
    }
    js("browser", IR) {
        browser { }
        binaries.executable()
    }
    js("node", IR) {
        nodejs { }
        binaries.executable()
    }
}
