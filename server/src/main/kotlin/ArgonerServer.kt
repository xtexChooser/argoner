package argoner.server

import argoner.server.config.ServerConfig
import com.typesafe.config.ConfigFactory
import io.javalin.Javalin
import io.javalin.core.compression.CompressionStrategy
import io.javalin.core.util.Headers
import io.javalin.core.util.RouteOverviewPlugin
import io.javalin.http.staticfiles.Location
import io.javalin.http.util.RedirectToLowercasePathPlugin
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig
import mu.KotlinLogging
import okio.Path.Companion.toPath

object ArgonerServer {

    val logger = KotlinLogging.logger("ArgonerServer")

    val basePath = (System.getProperty("argoner.base_path") ?: ".").toPath(normalize = true)

    @OptIn(ExperimentalSerializationApi::class)
    val config = Hocon.decodeFromConfig<ServerConfig>(ConfigFactory.parseFile((basePath / "config.conf").toFile()))
    val devMode get() = config.devMode

    val javalin = createJavalin()

    fun start() {
        logger.info("Server starting...")
        if (devMode) {
            logger.warn("Running in DEV mode")
        }
        javalin.start(config.http.host, config.http.port)
    }

    private fun createJavalin(): Javalin =
        Javalin.create {
            it.autogenerateEtags = config.http.generateETags
            it.prefer405over404 = config.http.prefer405over404
            it.enforceSsl = config.http.forceSsl
            it.showJavalinBanner = false
            it.contextPath = config.http.contextPath
            // if (devMode) it.enableDevLogging()
            it.compressionStrategy(if (config.http.useGzip) CompressionStrategy.GZIP else CompressionStrategy.NONE)
            it.globalHeaders {
                Headers().apply {
                    headers += "X-Server" to "Argoner"
                    headers.putAll(config.http.headers)
                    xPermittedCrossDomainPolicies(config.http.corsPolicy)
                }
            }
            it.enableHttpAllowedMethodsOnRoutes()
            if (!config.http.noBuiltinResources) {
                it.addStaticFiles { static ->
                    static.hostedPath = "/"
                    static.directory = "META-INF/argoner/frontend"
                    static.location = Location.CLASSPATH
                    static.precompress = config.http.precompressResources && !config.devMode
                }
            }
            if (config.http.extraResources != null)
                it.addStaticFiles(config.http.extraResources, Location.EXTERNAL)
            it.registerPlugin(RouteOverviewPlugin("/routes"))
            it.registerPlugin(RedirectToLowercasePathPlugin())
        }

}