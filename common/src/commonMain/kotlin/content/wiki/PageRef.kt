package argoner.common.content.wiki

import kotlinx.serialization.Serializable

@Serializable
data class PageRef(
    val wiki: WikiID,
    val page: PageID,
)
