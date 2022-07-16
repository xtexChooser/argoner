package argoner.server.handler.wiki

import argoner.server.wiki.Wikis
import io.javalin.http.Context
import io.javalin.http.HttpCode

fun handleListWiki(ctx: Context) {
    ctx.json(Wikis.wikis.map { it.value.installation }.toTypedArray())
}

fun handleWikiInfo(ctx: Context) {
    val wiki = Wikis.getOrNull(ctx.pathParam("wiki"))
    if (wiki == null) {
        ctx.status(HttpCode.NOT_FOUND)
    } else {
        ctx.json(wiki)
    }
}