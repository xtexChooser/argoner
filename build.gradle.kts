import org.jetbrains.compose.jetbrainsCompose
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    kotlin("multiplatform") version "1.7.10" apply false
    id("org.jetbrains.compose") version "1.2.0-alpha01-dev745" apply false
    kotlin("plugin.serialization") version "1.7.10" apply false
}

allprojects {
    group = "com.xtex"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
        jetbrainsCompose()
    }

    afterEvaluate {
        if (pluginManager.hasPlugin("org.jetbrains.kotlin.jvm")) {
            tasks.withType(KotlinJvmCompile::class.java) {
                kotlinOptions {
                    jvmTarget = "17"
                }
            }
            tasks.withType(KotlinJsCompile::class.java) {
                kotlinOptions {
                    moduleKind ="umd"
                    sourceMap = true
                    metaInfo = true
                }
            }
        }
    }
}
