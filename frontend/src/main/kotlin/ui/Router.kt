package argoner.frontend.web.ui

import androidx.compose.runtime.Composable
import app.softwork.routingcompose.RouteBuilder
import app.softwork.routingcompose.Routing
import argoner.frontend.web.ui.page.AboutPage
import argoner.frontend.web.ui.page.IndexPage
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Routing
@Composable
fun RouteBuilder.AppRouter() {
    route("/") { IndexPage() }
    route("/about") { AboutPage() }
    noMatch {
        P {
            Text("Router Not Found")
        }
    }
}
