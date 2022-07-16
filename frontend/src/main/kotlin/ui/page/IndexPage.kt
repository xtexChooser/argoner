package argoner.frontend.web.ui.page

import androidx.compose.runtime.*
import app.softwork.routingcompose.NavLink
import argoner.client.endpoint.wiki.listWikis
import argoner.common.content.wiki.WikiInstallation
import argoner.frontend.web.FrontendClient
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.*
import kotlin.math.min

private val KEY_LOADING_EFFECT = Any()
private val KEY_LOADING_WIKIS = Any()

@Composable
fun IndexPage() {
    var wikis by remember { mutableStateOf<Set<WikiInstallation>?>(null) }
    if (wikis == null) {
        var progress by remember { mutableStateOf(0.0) }
        LaunchedEffect(KEY_LOADING_EFFECT) {
            while (progress < 99) {
                progress++
                delay(50)
            }
            while (true) {
                progress += 0.001
                delay(50)
            }
        }
        LaunchedEffect(KEY_LOADING_WIKIS) {
            wikis = FrontendClient.listWikis()
        }
        Div(attrs = { classes("progress", "margin-bottom") }) {
            Div(attrs = { classes("bar", "muted", "w-${min(progress.toInt(), 100)}") }) { Text("$progress%") }
        }
    } else {
        wikis!!.forEach { wiki ->
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