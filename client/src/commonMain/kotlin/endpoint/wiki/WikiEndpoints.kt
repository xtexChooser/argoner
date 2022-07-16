package argoner.client.endpoint.wiki

import argoner.client.ArgonerClient
import argoner.client.call
import argoner.common.content.wiki.WikiID
import argoner.common.content.wiki.WikiInstallation

suspend fun ArgonerClient.listWikis() = call<Set<WikiInstallation>>("wiki")

suspend fun ArgonerClient.getWikiInfo(wikiID: WikiID) = call<WikiInstallation>("wiki/$wikiID")