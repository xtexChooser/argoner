package argoner.server.wiki

import argoner.server.config.ProxyConfig
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class WikiConfig(
    val name: String,
    val url: String,
    val apiUrl: String = "$url/w/api.php",
    val proxy: ProxyConfig? = null,
) {

    @Transient
    lateinit var id: String

}
