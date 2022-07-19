package argoner.frontend.web.ui

import androidx.compose.runtime.Composable
import app.softwork.routingcompose.RouteBuilder
import app.softwork.routingcompose.Routing
import argoner.frontend.web.ui.page.AboutPage
import argoner.frontend.web.ui.page.IndexPage
import argoner.frontend.web.ui.page.wiki.WikiIndexPage
import argoner.frontend.web.ui.page.wiki.WikiIssuesPage
import org.jetbrains.compose.web.dom.Text

@Routing
@Composable
fun RouteBuilder.AppRouter() {
    route("/") { IndexPage() }
    route("/about") { AboutPage() }
    route("/wiki/") {
        string { wikiID ->
            route("/") { WikiIndexPage(wikiID) }
            route("/issues") { WikiIssuesPage(wikiID) }
            noMatch()
        }
    }
    noMatch()
}

@Routing
@Composable
private fun RouteBuilder.noMatch() {
    noMatch { Text("Router Not Found") }
}
