package argoner.frontend.web.ui.components.issue

import androidx.compose.runtime.Composable
import argoner.common.content.issue.DescribedIssue
import argoner.frontend.web.ui.components.desc.DescriptorsInfo
import org.jetbrains.compose.web.attributes.ATarget
import org.jetbrains.compose.web.attributes.target
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun DescribedIssueInfo(issue: DescribedIssue) {
    Div {
        A(href = issue.pageUrl, attrs = {
            classes("paper-btn", "btn-secondary", "btn-small")
            target(ATarget.Blank)
        }) { Text(issue.page.page) }
        Span(attrs = { classes("margin-left-small") }) { Text(issue.summary) }
    }
    DescriptorsInfo(issue.descriptors)
    Div(attrs = { classes("margin-top", "margin-bottom-small", "text-muted") }) {
        Div(attrs = { classes("row") }) {
            Div(attrs = { classes("col-5", "col", "padding-none") }) {
                Span {
                    Text("Issue UUID: ${issue.uuid}")
                }
            }
            Div(attrs = { classes("col-5", "col", "padding-none") }) {
                Span {
                    Text("Source: ${issue.source}")
                }
            }
        }
        Div(attrs = { classes("row") }) {
            Div(attrs = { classes("col-5", "col", "padding-none") }) {
                Span {
                    Text("First Found: ${issue.firstFoundTime}")
                }
            }
            Div(attrs = { classes("col-5", "col", "padding-none") }) {
                Span {
                    Text("Last Checked: ${issue.lastCheckedTime}")
                }
            }
        }
    }
}