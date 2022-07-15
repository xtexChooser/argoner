plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("application")
}

dependencies {
    testImplementation(kotlin("test"))

    api(project(":common"))
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.18.0")
    implementation("io.javalin:javalin:4.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-hocon:1.3.3")
    implementation("com.squareup.okio:okio:3.2.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.10")
    implementation("org.jetbrains.exposed:exposed-core:0.38.2")
    implementation("org.jetbrains.exposed:exposed-dao:0.38.2")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.38.2")
    implementation("app.softwork:kotlinx-uuid-exposed:0.0.16-sqldelight2a03")
    implementation("org.postgresql:postgresql:42.4.0")
    implementation("com.h2database:h2:2.1.214")
    implementation("mysql:mysql-connector-java:8.0.29")
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

    manifest {
        attributes += "Implementation-Version" to project.version
    }
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
