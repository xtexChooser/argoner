package argoner.server.issue.details

import argoner.common.content.desc.AnyDescriptor
import argoner.common.util.Identifier
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable

@Serializable
@Polymorphic
abstract class IssueDetails {

    abstract fun summarize(): String
    abstract fun describe(): Map<Identifier, AnyDescriptor>

}
