package argoner.frontend.web.ui.components.util

import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import kotlin.math.min

@Composable
inline fun <T> LoadingData(crossinline loader: suspend () -> T, content: @Composable (T) -> Unit) {
    val keyLoadingEffect by remember { derivedStateOf { Any() } }
    val keyLoadingData by remember { derivedStateOf { Any() } }
    var data by remember { mutableStateOf<T?>(null) }
    if (data == null) {
        var progress by remember { mutableStateOf(0.0) }
        LaunchedEffect(keyLoadingEffect) {
            while (progress < 99) {
                progress++
                delay(50)
            }
            while (true) {
                progress += 0.001
                delay(50)
            }
        }
        LaunchedEffect(keyLoadingData) {
            data = loader()
        }
        Div(attrs = { classes("progress", "margin-bottom") }) {
            Div(attrs = { classes("bar", "muted", "w-${min(progress.toInt(), 100)}") }) { Text("$progress%") }
        }
    } else {
        content(data!!)
    }
}