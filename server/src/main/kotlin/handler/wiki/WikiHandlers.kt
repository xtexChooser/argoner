package argoner.server.handler.wiki

import argoner.server.wiki.Wikis
import io.javalin.http.Context

fun handleListWiki(ctx: Context) {
    ctx.json(Wikis.wikis.map { it.value.installation }.toTypedArray())
}

fun handleWikiInfo(ctx: Context) {
    val wiki = Wikis[ctx.pathParam("wiki")]
    ctx.json(wiki.installation)
}