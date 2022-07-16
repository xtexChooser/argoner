package argoner.server

import argoner.server.config.ServerConfig
import argoner.server.handler.handleServerInfo
import argoner.server.handler.wiki.handleListWiki
import argoner.server.handler.wiki.handleWikiInfo
import argoner.server.issue.details.registerIssueDetailsSerializer
import argoner.server.module.TestMod
import argoner.server.util.BuildConfig
import argoner.server.util.component.ComponentContainer
import argoner.server.util.javalin.JsonMapper
import com.typesafe.config.ConfigFactory
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.core.compression.CompressionStrategy
import io.javalin.core.util.Headers
import io.javalin.core.util.RouteOverviewPlugin
import io.javalin.http.staticfiles.Location
import io.javalin.http.util.RedirectToLowercasePathPlugin
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import mu.KotlinLogging
import okio.Path.Companion.toPath

object ArgonerServer : ComponentContainer<ArgonerServer>() {

    val logger = KotlinLogging.logger("ArgonerServer")

    val basePath = (System.getProperty("argoner.base_path") ?: ".").toPath(normalize = true)

    @OptIn(ExperimentalSerializationApi::class)
    val config = Hocon.decodeFromConfig<ServerConfig>(ConfigFactory.parseFile((basePath / "config.conf").toFile()))
    val devMode get() = config.devMode

    val serializer = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
        serializersModule = SerializersModule {
            registerIssueDetailsSerializer()
        }
    }
    val javalin = createJavalinServer()

    fun start() {
        logger.info("Server starting...")
        logger.info("Version: ${BuildConfig.VERSION}")
        if (devMode) {
            logger.warn("Running in DEV mode")
        }
        registerRoutes()
        javalin.start(config.http.host, config.http.port)
        config.database.connect()
        TestMod.init()
    }

    private fun createJavalinServer(): Javalin =
        Javalin.create {
            it.autogenerateEtags = config.http.generateETags
            it.prefer405over404 = config.http.prefer405over404
            it.enforceSsl = config.http.enforceSsl
            it.contextPath = config.http.contextPath
            // if (devMode) it.enableDevLogging()
            it.compressionStrategy(if (config.http.useGzip) CompressionStrategy.GZIP else CompressionStrategy.NONE)
            it.globalHeaders {
                Headers().apply {
                    headers += "X-Server" to "Argoner/${BuildConfig.VERSION}"
                    headers.putAll(config.http.headers)
                    xPermittedCrossDomainPolicies(config.http.corsPolicy)
                }
            }
            it.jsonMapper(JsonMapper(serializer))
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
            it.registerPlugin(RouteOverviewPlugin("/api"))
            it.registerPlugin(RedirectToLowercasePathPlugin())
        }

    private fun registerRoutes() {
        javalin.routes {
            path("api") {
                get("info", ::handleServerInfo)
                path("wiki") {
                    get(::handleListWiki)
                    path("{wiki}") {
                        get(::handleWikiInfo)
                    }
                }
            }
        }
    }

}