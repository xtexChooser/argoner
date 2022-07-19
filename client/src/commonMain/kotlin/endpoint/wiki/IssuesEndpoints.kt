package argoner.client.endpoint.wiki

import argoner.client.ArgonerClient
import argoner.client.call
import argoner.common.content.issue.DescribedIssue
import argoner.common.content.wiki.WikiID

suspend fun ArgonerClient.countIssues(wikiID: WikiID) =
    call<Int>("v1/wiki/$wikiID/issues/count")

suspend fun ArgonerClient.listIssues(wikiID: WikiID, offset: Int? = null, count: Int? = null) =
    call<List<DescribedIssue>>(
        "v1/wiki/$wikiID/issues", queryParams = buildMap {
            if (offset != null) put("offset", offset.toString())
            if (count != null) put("count", count.toString())
        }
    )