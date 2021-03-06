package argoner.server.module

import argoner.common.content.desc.AnyDescriptor
import argoner.common.content.desc.StringDesc
import argoner.common.content.issue.BotIssueSource
import argoner.common.util.Identifier
import argoner.server.issue.db.IssueRecords
import argoner.server.issue.details.IssueDetails
import argoner.server.wiki.Wikis
import kotlinx.datetime.Clock
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.transactions.transaction

object TestMod : Module() {

    override val namespace: String
        get() = "test"

    override fun ContentRegisterer.register() {
        val testType = IssueType(Identifier("test"), TestDet::class, "Test Issue Type")
        Wikis.wikis.values.forEach {
            transaction {
                for (i in 0..3) {
                    it[IssueRecords].new {
                        page = "Minecraft Wiki"
                        source = BotIssueSource(identity("test_scanner"))
                        type = testType
                        details = TestDet("this is a test issue ${Clock.System.now().epochSeconds}")
                    }
                    it[IssueRecords].new {
                        page = "MCW:ANB"
                        source = BotIssueSource(identity("test_scanner"))
                        type = testType
                        details = TestDet("this is another test issue ${Clock.System.now().epochSeconds}")
                    }

                }
            }
        }
    }

    @Serializable
    @SerialName("test:test")
    data class TestDet(val text: String) : IssueDetails() {
        override fun summarize(): String {
            return "Test Issue: $text"
        }

        override fun describe(): Map<Identifier, AnyDescriptor> {
            return mapOf(identity("text") to StringDesc(text))
        }
    }

}