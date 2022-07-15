package argoner.common.content.issue.source

import argoner.common.util.Identifier
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("bot")
class BotIssueSource(val scanner: Identifier) : IssueSource() {

    override fun toString() = "Bot($scanner)"

}