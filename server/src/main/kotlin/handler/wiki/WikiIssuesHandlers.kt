package argoner.server.handler.wiki

import argoner.server.wiki.Wikis
import io.javalin.http.Context
import io.javalin.http.HttpCode
import org.jetbrains.exposed.sql.transactions.transaction

fun handleIssuesCount(ctx: Context) {
    val wiki = Wikis[ctx.pathParam("wiki")]
    transaction {
        ctx.json(wiki.countIssues())
    }
}

fun handleIssuesList(ctx: Context) {
    val wiki = Wikis[ctx.pathParam("wiki")]
    val offset = ctx.queryParam("offset")?.toLong() ?: 0
    val count = ctx.queryParam("count")?.toInt() ?: 20
    if (count > 200) {
        ctx.status(HttpCode.BAD_REQUEST)
            .result("Up to 200 issues once query")
        return
    }
    val result = transaction { wiki.listIssues().limit(count, offset).map { it.described } }
    ctx.json(result.toTypedArray())
}