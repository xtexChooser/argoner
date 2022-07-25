package argoner.frontend.web.ui.components.util

import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import kotlin.math.min

@Composable
fun <T> LoadingData(loader: suspend () -> T, key: Any? = null, content: @Composable (T) -> Unit) {
    val keyLoadingEffect = remember { Any() }
    val keyLoadingData = remember { Any() }
    var data by remember(key) { mutableStateOf<T?>(null) }
    if (data == null) {
        var progress by remember { mutableStateOf(0.0) }
        LaunchedEffect(keyLoadingEffect, key) {
            while (progress < 99) {
                progress++
                delay(50)
            }
            while (true) {
                progress += 0.001
                delay(50)
            }
        }
        LaunchedEffect(keyLoadingData, key) {
            data = loader()
        }
        Div(attrs = { classes("progress", "margin-bottom") }) {
            Div(attrs = { classes("bar", "muted", "w-${min(progress.toInt(), 100)}") }) { Text("$progress%") }
        }
    } else {
        content(data!!)
    }
}