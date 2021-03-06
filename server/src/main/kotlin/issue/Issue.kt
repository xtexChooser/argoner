package argoner.server.issue

import argoner.common.content.issue.DescribedIssue
import argoner.common.content.issue.IssueID
import argoner.common.content.wiki.PageRef
import argoner.server.issue.db.IssueRecord
import argoner.server.wiki.WikiInstance
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class Issue(val wiki: WikiInstance, val record: IssueRecord) {

    val uuid: IssueID get() = record.id.value
    val pageID get() = record.page
    val source get() = record.source
    val pageRef get() = PageRef(wiki.id, pageID)
    val type get() = record.type
    val details get() = record.details
    val firstFoundTime by lazy { Instant.fromEpochSeconds(record.firstFoundTime) }

    override fun hashCode() = record.hashCode()
    override fun equals(other: Any?) = other is Issue && other.record == record
    override fun toString() = "Issue($record)"

    val described by lazy { describe() }

    private fun describe() = DescribedIssue(
        uuid = uuid,
        page = pageRef,
        pageUrl = wiki.config.url + "/wiki/" + pageID,
        source = source,
        title = type.name,
        summary = details.summarize(),
        firstFoundTime = firstFoundTime,
        lastCheckedTime = Clock.System.now(),
        descriptors = details.describe(),
    )

}