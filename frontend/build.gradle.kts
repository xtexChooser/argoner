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
    api(project(":client"))
    implementation(compose.web.core)
    implementation("app.softwork:routing-compose:0.2.6")
}

compose {
    experimental {
        web {
        }
    }
}

tasks.create<Download>("downloadLibs") {
    src(arrayOf(
        "https://cdn.jsdelivr.net/npm/papercss@1.8.3/dist/paper.min.css",
        "https://cdn.jsdelivr.net/npm/browser-update@3.3.38/update.npm.full.min.js"
    ))
    dest(File(buildDir, "libraries"))
    dest.mkdirs()
    onlyIfModified(true)
    retries(2)
    tasks["processResources"].dependsOn(this)
    tasks.getByName<Copy>("processResources").from(this)
}
