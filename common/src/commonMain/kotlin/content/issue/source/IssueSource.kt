package argoner.common.content.issue.source

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable

@Serializable
@Polymorphic
sealed class IssueSource {

    abstract override fun toString(): String

}
