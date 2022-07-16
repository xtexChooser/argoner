package argoner.frontend.web.ui.page

import androidx.compose.runtime.Composable
import app.softwork.routingcompose.NavLink
import argoner.client.endpoint.wiki.listWikis
import argoner.frontend.web.FrontendClient
import argoner.frontend.web.ui.components.util.LoadingData
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.*

@Composable
fun IndexPage() {
    LoadingData({ FrontendClient.listWikis() }) { wikis ->
        wikis.forEach { wiki ->
            Div(attrs = {
                classes("card")
                style {
                    width(20.cssRem)
                }
            }) {
                Div(attrs = { classes("card-body") }) {
                    H4(attrs = { classes("card-title") }) { Text(wiki.name) }
                    H5(attrs = { classes("card-subtitle") }) { Text(wiki.id) }
                    P(attrs = { classes("card-text") }) { Text(wiki.url) }
                    NavLink(to = "/wiki/${wiki.id}", attrs = { classes("card-link") }) { Text("Open") }
                }
            }
        }
    }
}