package argoner.common.content.issue

import argoner.common.util.Identifier
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class IssueSource {

    abstract override fun toString(): String

}

@Serializable
@SerialName("bot")
class BotIssueSource(val scanner: Identifier) : IssueSource() {

    override fun toString() = "Bot($scanner)"

}
