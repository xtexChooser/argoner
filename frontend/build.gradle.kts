import de.undercouch.gradle.tasks.download.Download
import org.jetbrains.compose.compose

plugins {
    kotlin("js")
    id("org.jetbrains.compose")
    id("de.undercouch.download")
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
}

compose {
    experimental {
        web {
        }
    }
}

tasks.create<Download>("downloadPicnic") {
    src(arrayOf(
        "https://cdn.jsdelivr.net/npm/picnic@7.1.0/picnic.min.css"
    ))
    dest(File(buildDir, "libraries"))
    dest.mkdirs()
    onlyIfModified(true)
    tasks["processResources"].dependsOn(this)
    tasks.getByName<Copy>("processResources").from(this)
}
