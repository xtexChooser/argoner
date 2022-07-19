package argoner.server.issue.db

import argoner.common.content.issue.IssueSource
import argoner.common.util.Identifier
import argoner.server.util.component.Component
import argoner.server.util.component.ComponentType
import argoner.server.util.db.json
import argoner.server.util.db.jsonAsString
import argoner.server.wiki.WikiInstance
import kotlinx.datetime.Clock
import kotlinx.uuid.exposed.KotlinxUUIDEntityClass
import kotlinx.uuid.exposed.KotlinxUUIDTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class IssueRecords(val wiki: WikiInstance, table: Table = Table(wiki)) :
    KotlinxUUIDEntityClass<IssueRecord>(table, IssueRecord::class.java, { IssueRecord(table, it) }),
    Component<WikiInstance> {

    companion object Type : ComponentType<WikiInstance, IssueRecords>(Identifier("issue_records"), ::IssueRecords)

    class Table(wiki: WikiInstance) : KotlinxUUIDTable(name = "${wiki.id}_issue_records", columnName = "uuid") {

        val page = varchar("page_id", 255)
        val issueSource = json<IssueSource>("source")
        val type = varchar("type", 64)
            .index("type", false)
        val details = jsonAsString("details")
        val firstFoundTime = long("first_found_time")
            .clientDefault { Clock.System.now().epochSeconds }

        init {
            transaction {
                SchemaUtils.create(this@Table)
            }
        }

    }

}
