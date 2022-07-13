import org.jetbrains.compose.compose

plugins {
    kotlin("js")
    id("org.jetbrains.compose")
}

kotlin {
    js(IR) {
        browser {
            testTask {
                onlyIf { false }
            }
        }
        binaries.executable()
    }
}

dependencies {
    implementation(compose.web.core)
    implementation("app.softwork:routing-compose:0.2.6")
    implementation(npm("picnic", "7.1.0"))
}

compose {
    experimental {
        web {
        }
    }
}
