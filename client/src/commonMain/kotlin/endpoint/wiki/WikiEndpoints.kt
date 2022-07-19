package argoner.client.endpoint.wiki

import argoner.client.ArgonerClient
import argoner.client.call
import argoner.common.content.wiki.WikiID
import argoner.common.content.wiki.WikiInstallation

suspend fun ArgonerClient.listWikis() = call<List<WikiInstallation>>("v1/wiki")

suspend fun ArgonerClient.getWikiInfo(wikiID: WikiID) = call<WikiInstallation>("v1/wiki/$wikiID")