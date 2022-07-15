package argoner.common.content.desc

import argoner.common.content.issue.source.IssueSource
import argoner.common.content.wiki.PageRef
import argoner.common.content.wiki.WikiInstallation
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.uuid.UUID

@Serializable
@SerialName("page")
class PageRefDesc(override val value: PageRef) : Descriptor<PageRef>()

@Serializable
@SerialName("wiki_installation")
class WikiInstallationDesc(override val value: WikiInstallation) : Descriptor<WikiInstallation>()

@Serializable
@SerialName("issue_source")
class IssueSourceDesc(override val value: IssueSource) : Descriptor<IssueSource>()

@Serializable
@SerialName("uuid")
class UUIDDesc(override val value: UUID) : Descriptor<UUID>()