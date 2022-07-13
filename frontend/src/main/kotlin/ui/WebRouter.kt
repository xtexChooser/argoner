package argoner.frontend.web.ui

import androidx.compose.runtime.Composable
import app.softwork.routingcompose.HashRouter
import app.softwork.routingcompose.RouteBuilder
import app.softwork.routingcompose.Routing
import app.softwork.routingcompose.route
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun WebRouter() {
    androidx.compose.material3.Text("test")
    HashRouter().route("/") {
        Routing()
    }
}

@Routing
@Composable
fun RouteBuilder.Routing() {
    route("test") {
        /*P {
            Text("Hello World")
        }*/
        Text("test")
    }
    noMatch {
        P {
            Text("test")
            Text("No Routers Found")
        }
    }
}
