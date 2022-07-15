package argoner.common.endpoint

import kotlinx.serialization.Serializable

@Serializable
data class ServerInfo(
    val server: String,
    val version: String,
    val devMode: Boolean,
    val userAgent: String,
    val contextPath: String,
    val enforceSsl: Boolean,
    val corsPolicy: String,
)
