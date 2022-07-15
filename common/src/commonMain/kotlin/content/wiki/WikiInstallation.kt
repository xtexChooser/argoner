package argoner.common.content.wiki

import kotlinx.serialization.Serializable

@Serializable
data class WikiInstallation(
    val id: String,
    val name: String,
    val url: String,
    val viewUrl: String,
)
