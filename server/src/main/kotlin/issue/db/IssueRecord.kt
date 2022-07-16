package argoner.server.issue.db

import argoner.common.content.wiki.PageID
import argoner.common.util.Identifier
import argoner.common.util.observableLazy
import argoner.server.ArgonerServer
import argoner.server.issue.details.IssueDetails
import argoner.server.issue.type.IssueType
import kotlinx.serialization.KSerializer
import kotlinx.uuid.UUID
import kotlinx.uuid.exposed.KotlinxUUIDEntity
import org.jetbrains.exposed.dao.id.EntityID

class IssueRecord(table: IssueRecords.Table, id: EntityID<UUID>) : KotlinxUUIDEntity(id) {

    var page: PageID by table.page
    var source by table.issueSource
    var typeID by table.type
    var type by observableLazy({ IssueType[Identifier(typeID)]!! }) { _, _, newValue ->
        typeID = newValue.id.toString()
    }
    var detailsJson by table.details
    var details by observableLazy({ ArgonerServer.serializer.decodeFromString(type.detailsSerializer, detailsJson) })
    { _, _, newValue ->
        @Suppress("UNCHECKED_CAST")
        detailsJson =
            ArgonerServer.serializer.encodeToString(type.detailsSerializer as KSerializer<IssueDetails>, newValue)
    }
    var firstFoundTime by table.firstFoundTime

    override fun hashCode() = id.hashCode()
    override fun equals(other: Any?) = other is IssueRecord && other.id == id
    override fun toString() = "IssueRecord($id)"

}