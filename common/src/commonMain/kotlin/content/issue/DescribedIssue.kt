package argoner.common.content.issue

import argoner.common.content.desc.AnyDescriptor
import argoner.common.content.issue.source.IssueSource
import argoner.common.content.wiki.PageRef
import argoner.common.util.Identifier
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class DescribedIssue(
    val uuid: IssueID,
    val page: PageRef,
    val source: IssueSource,
    val summary: String,
    val firstFoundTime: Instant,
    val lastCheckedTime: Instant,
    val descriptors: Map<Identifier, AnyDescriptor>
)
