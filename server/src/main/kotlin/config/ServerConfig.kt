package argoner.server.config

import argoner.server.util.BuildConfig
import argoner.server.wiki.WikiConfig
import kotlinx.serialization.Serializable

@Serializable
data class ServerConfig(
    val devMode: Boolean = System.getenv("ARGONER_DEV_MODE")?.toBoolean() ?: false,
    val http: HttpConfig = HttpConfig(),
    val database: DatabaseConfig,
    val wikis: Map<String, WikiConfig>,
    val userAgent: String = "Argoner/${BuildConfig.VERSION}"
) {

    init {
        wikis.forEach { id, wiki -> wiki.id = id }
    }

}
