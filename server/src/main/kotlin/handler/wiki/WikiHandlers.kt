package argoner.server.handler.wiki

import argoner.server.wiki.Wikis
import io.javalin.http.Context

fun handleListWiki(ctx: Context) {
    ctx.json(Wikis.wikis.map { it.value.installation }.toTypedArray())
}