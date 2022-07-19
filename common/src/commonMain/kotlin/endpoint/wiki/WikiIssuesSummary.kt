package argoner.common.endpoint.wiki

import kotlinx.serialization.Serializable

@Serializable
data class WikiIssuesSummary(
    val count: Long,
)
