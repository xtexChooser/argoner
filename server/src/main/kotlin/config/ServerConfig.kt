package argoner.server.config

import kotlinx.serialization.Serializable

@Serializable
data class ServerConfig(
    val devMode: Boolean = System.getenv("ARGONER_DEV_MODE")?.toBoolean() ?: false,
    val http: HttpConfig = HttpConfig(),
)
