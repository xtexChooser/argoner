package argoner.frontend.web.ui

import androidx.compose.runtime.Composable
import app.softwork.routingcompose.HashRouter
import app.softwork.routingcompose.NavLink
import app.softwork.routingcompose.Router
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.name
import org.jetbrains.compose.web.dom.*

@Composable
fun App() {
    HashRouter("/") {
        Nav(attrs = { classes("border", "fixed", "split-nav") }) {
            Div(attrs = { classes("nav-brand") }) {
                A {
                    Img(src = "./icon.png")
                    Span { Text("Argoner") }
                }
            }
            /*Div(attrs = { classes("collapsible") }) {
                Input(type = InputType.Checkbox, attrs = {
                    id("menu-collapsible")
                    name("menu-collapsible")
                })
                Label(forId = "menu-collapsible") {
                    for (i in 1..3) {
                        Div(attrs = { classes("bar$i") }) { Text("-") }
                    }
                }
                Div(attrs = { classes("collapsible-body") }) {*/
                    Ul(attrs = { classes("inline") }) {
                        Li { NavLink("/about") { Text("About") } }
                    }
                /*}
            }*/
        }
        AppRouter()
    }
}