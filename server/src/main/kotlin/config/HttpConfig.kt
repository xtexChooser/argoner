package argoner.server.config

import io.javalin.core.util.Headers
import kotlinx.serialization.Serializable

@Serializable
data class HttpConfig(
    val host: String = "127.0.0.1",
    val port: Int = 8080,
    val generateETags: Boolean = false,
    val prefer405over404: Boolean = false,
    val enforceSsl: Boolean = false,
    val contextPath: String = "/",
    val useGzip: Boolean = true,
    val corsPolicy: Headers.CrossDomainPolicy = Headers.CrossDomainPolicy.MASTER_ONLY,
    val headers: Map<String, String> = emptyMap(),
    val precompressResources: Boolean = false,
    val noBuiltinResources: Boolean = false,
    val extraResources: String? = null,
)
