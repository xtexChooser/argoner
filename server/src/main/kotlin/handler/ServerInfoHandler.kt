package argoner.server.handler

import argoner.common.endpoint.ServerInfo
import argoner.server.ArgonerServer
import argoner.server.util.BuildConfig
import io.javalin.http.Context

fun handleServerInfo(ctx: Context) {
    ctx.json(
        ServerInfo(
            server = "Argoner",
            version = BuildConfig.VERSION,
            devMode = ArgonerServer.config.devMode,
            userAgent = ArgonerServer.config.userAgent,
            contextPath = ArgonerServer.config.http.contextPath,
            enforceSsl = ArgonerServer.config.http.enforceSsl,
            corsPolicy = ArgonerServer.config.http.corsPolicy.name.lowercase().replace("_", "-"),
        )
    )
}