package argoner.server.wiki

import argoner.common.content.wiki.WikiID
import argoner.common.content.wiki.WikiInstallation
import argoner.server.issue.Issue
import argoner.server.issue.db.IssueRecords
import argoner.server.util.HttpUserAgentInterceptor
import argoner.server.util.component.ComponentContainer
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.mapLazy
import org.jetbrains.exposed.sql.transactions.transaction

class WikiInstance(val config: WikiConfig) : ComponentContainer<WikiInstance>() {

    val id: WikiID get() = config.id
    val installation = WikiInstallation(id = id, name = config.name, url = config.url)

    val httpClient = OkHttpClient.Builder()
        .proxy(config.proxy?.toJavaProxy())
        .addInterceptor(HttpUserAgentInterceptor)
        .build()

    val apiBaseUrl = config.apiUrl.toHttpUrl()

    fun countIssues(filter: Op<Boolean>? = null) = transaction { get(IssueRecords).count(filter).toInt() }
    fun listIssues(filter: Op<Boolean>? = null) =
        transaction {
            if (filter != null) {
                get(IssueRecords).find(filter)
            } else {
                get(IssueRecords).all()
            } mapLazy {
                Issue(this@WikiInstance, it)
            }
        }

}