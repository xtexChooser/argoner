package argoner.frontend.web.ui.page.wiki

import androidx.compose.runtime.Composable
import argoner.client.endpoint.wiki.countIssues
import argoner.common.content.wiki.WikiID
import argoner.frontend.web.FrontendClient
import argoner.frontend.web.ui.components.issue.WikiIssuesList
import argoner.frontend.web.ui.components.util.LoadingData
import argoner.frontend.web.ui.components.util.PagedData
import kotlin.math.roundToInt

@Composable
fun WikiIssuesPage(wikiID: WikiID) {
    LoadingData({ FrontendClient.countIssues(wikiID) }) { issuesCount ->
        PagedData((issuesCount / 20.0).roundToInt()) {
            WikiIssuesList(wikiID, offset = it * 20)
        }
    }
}