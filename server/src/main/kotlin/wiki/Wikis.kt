package argoner.server.wiki

import argoner.server.ArgonerServer
import argoner.server.util.NotFoundException

object Wikis {

    val wikis = ArgonerServer.config.wikis.mapValues { (_, wiki) -> WikiInstance(wiki) }

    operator fun get(id: String) = getOrNull(id) ?: throw NotFoundException("Wiki '$id' not found")
    fun getOrNull(id: String) = wikis[id]

}