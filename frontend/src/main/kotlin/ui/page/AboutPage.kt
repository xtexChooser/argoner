package argoner.frontend.web.ui.page

import androidx.compose.runtime.*
import argoner.client.endpoint.getServerInfo
import argoner.common.endpoint.ServerInfo
import argoner.frontend.web.FrontendClient
import argoner.frontend.web.ui.components.papercss.ModalDialog
import argoner.frontend.web.util.backOrHome
import argoner.frontend.web.util.history
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.fontStyle
import org.jetbrains.compose.web.dom.*

private val KEY_LOADING_EXTRA_SERVER_INFO = Any()

@Composable
fun AboutPage() {
    ModalDialog(
        id = "about-server-dialog",
        opened = derivedStateOf { true },
        title = { Text("Argoner") },
        subtitle = { Text("The tool for everyone.") },
        onClose = { history.backOrHome() }
    ) {
        var serverInfo by remember { mutableStateOf<ServerInfo?>(null) }
        if (serverInfo == null) {
            LaunchedEffect(KEY_LOADING_EXTRA_SERVER_INFO) {
                serverInfo = FrontendClient.getServerInfo()
            }
            Text("Loading more server information...")
        } else {
            val info = serverInfo!!
            Table {
                Tbody {
                    Tr {
                        Td { Text("Server Software") }
                        Td { Text("${info.server} / ${info.version}") }
                    }
                    Tr {
                        Td { Text("Build Type") }
                        Td(attrs = {
                            if (info.devMode) {
                                style {
                                    color(Color.red)
                                    fontStyle("italic")
                                }
                            }
                        }) { Text(if (info.devMode) "Dev" else "Production") }
                    }
                    Tr {
                        Td { Text("Outgoing User-Agent") }
                        Td { Text(info.userAgent) }
                    }
                    Tr {
                        Td { Text("Context Path") }
                        Td { Text(info.contextPath) }
                    }
                    Tr {
                        Td { Text("Backend Enforce SSL") }
                        Td { Text(info.enforceSsl.toString()) }
                    }
                    Tr {
                        Td { Text("Backend CORS Policy") }
                        Td { Text(info.corsPolicy) }
                    }
                }
            }
        }
        P {
            A(href = "./api/info") { Text("View server info as JSON") }
        }
    }
}