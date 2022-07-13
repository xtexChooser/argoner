plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("application")
}

dependencies {
    api(project(":common"))
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.18.0")
    implementation("io.javalin:javalin:4.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-hocon:1.3.3")
    implementation("com.squareup.okio:okio:3.2.0")
    testImplementation(kotlin("test"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

application {
    applicationName = "argoner-server"
    mainClass.set("argoner.server.main.MainKt")
    executableDir = ""
}

tasks.getByName<Jar>("jar") {
    archiveBaseName.set("argoner-server")
}

tasks.getByName<Copy>("processResources") {
    val jsDist = project(":frontend").tasks.getByName("browserDistribution")
    dependsOn(jsDist)
    from(jsDist.outputs) {
        into("META-INF/argoner/frontend")
        exclude("META-INF")
    }
}

tasks.getByName<JavaExec>("run") {
    environment("ARGONER_DEV_MODE", "true")
    workingDir(file("run"))
}
