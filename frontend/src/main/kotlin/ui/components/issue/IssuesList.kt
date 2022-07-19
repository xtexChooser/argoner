package argoner.frontend.web.ui.components.issue

import androidx.compose.runtime.Composable
import argoner.client.endpoint.wiki.listIssues
import argoner.common.content.issue.DescribedIssue
import argoner.common.content.wiki.WikiID
import argoner.frontend.web.FrontendClient
import argoner.frontend.web.ui.components.papercss.Collapsible
import argoner.frontend.web.ui.components.util.LoadingData
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

@Composable
fun WikiIssuesList(wikiID: WikiID, offset: Int? = null, count: Int? = null) {
    LoadingData({ FrontendClient.listIssues(wikiID, offset, count) }) { issues ->
        Div(attrs = {
            classes("row")
            style {
                display(DisplayStyle.Block)
            }
        }) {
            issues.forEach {
                WikiIssuesListElement(it)
            }
        }
    }
}

@Composable
fun WikiIssuesListElement(issue: DescribedIssue) {
    Collapsible(
        id = "collapsible-issue-${issue.uuid}",
        title = { Text("${issue.page.page} - ${issue.title}") },
        attrs = { classes("border") },
    ) {
        DescribedIssueInfo(issue)
    }
}
