package argoner.server.issue.details

import argoner.server.issue.type.IssueType
import kotlinx.serialization.modules.SerializersModuleBuilder
import kotlinx.serialization.modules.polymorphic

fun SerializersModuleBuilder.registerIssueDetailsSerializer() {
    polymorphic(IssueDetails::class) {
        IssueType.distinctBy { it.detailsType }
            .forEach {
                @Suppress("UNCHECKED_CAST")
                val v = it as IssueType<IssueDetails>
                subclass(v.detailsType, v.detailsSerializer)
            }
    }
}