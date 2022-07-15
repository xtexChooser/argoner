package argoner.server.wiki

import argoner.server.ArgonerServer

object Wikis {

    val wikis = ArgonerServer.config.wikis.mapValues { (_, wiki) -> WikiInstance(wiki) }

    operator fun get(id: String) = getOrNull(id) ?: throw IllegalArgumentException("Wiki '$id' not found")
    fun getOrNull(id: String) = wikis[id]

}