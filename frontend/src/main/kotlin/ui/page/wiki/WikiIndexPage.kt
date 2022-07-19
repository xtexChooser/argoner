package argoner.frontend.web.ui.page.wiki

import androidx.compose.runtime.Composable
import app.softwork.routingcompose.NavLink
import argoner.client.endpoint.wiki.getWikiInfo
import argoner.common.content.wiki.WikiID
import argoner.frontend.web.FrontendClient
import argoner.frontend.web.ui.components.util.LoadingData
import org.jetbrains.compose.web.dom.*

@Composable
fun WikiIndexPage(wikiID: WikiID) {
    LoadingData({ FrontendClient.getWikiInfo(wikiID) }) { info ->
        H4 { Text(info.name) }
        P { Text(info.id) }
        Ul(attrs = { classes("breadcrumb", "border") }) {
            Li { A(href = info.url, attrs = { attr("popover-top", info.url) }) { Text("Open") } }
            Li { NavLink(to = "issues") { Text("Issues") } }
        }
    }
}