package argoner.frontend.web.ui.components.issue

import androidx.compose.runtime.Composable
import argoner.common.content.issue.DescribedIssue
import argoner.frontend.web.ui.components.desc.DescriptorsInfo
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun DescribedIssueInfo(issue: DescribedIssue) {
    Div(attrs = { classes("row") }) {
        Div(attrs = { classes("col-4", "col") }) {
            A(href = issue.pageUrl, attrs = { attr("popover-top", issue.pageUrl) }) { Text(issue.page.page) }
        }
        Div(attrs = { classes("col-4", "col") }) {
            P(attrs = { attr("popover-top", "Issue UUID") }) { Text(issue.uuid.toString()) }
        }
        Div(attrs = { classes("col-4", "col") }) {
            P(attrs = { attr("popover-top", "Issue Discoverer") }) { Text(issue.source.toString()) }
        }
    }
    P { Text(issue.summary) }
    DescriptorsInfo(issue.descriptors)
    Div(attrs = { classes("row") }) {
        Div(attrs = { classes("col-6", "col") }) { P { Text("First Found at ${issue.firstFoundTime}") } }
        Div(attrs = { classes("col-6", "col") }) { P { Text("Last Checked at ${issue.lastCheckedTime}") } }
    }
}