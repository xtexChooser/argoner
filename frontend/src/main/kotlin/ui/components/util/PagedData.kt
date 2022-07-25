package argoner.frontend.web.ui.components.util

import androidx.compose.runtime.Composable
import app.softwork.routingcompose.NavLink
import app.softwork.routingcompose.Parameters
import app.softwork.routingcompose.Router
import org.jetbrains.compose.web.css.textAlign
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Ul
import kotlin.math.max
import kotlin.math.min

@Composable
fun PagedData(pageCount: Int, parameterName: String = "page", content: @Composable (Int) -> Unit) {
    val router = Router.current
    val currentPage =
        min(max(1, router.currentPath.parameters?.map?.get(parameterName)?.singleOrNull()?.toInt() ?: 1), pageCount)
    content(currentPage - 1)

    @Composable
    inline fun PageLink(page: Int, crossinline content: @Composable () -> Unit) {
        if (page == currentPage) {
            content()
        } else {
            NavLink(
                router.currentPath
                    .copy(
                        parameters = Parameters.from((router.currentPath.parameters?.map?.toMutableMap()
                            ?: mutableMapOf())
                            .also { it[parameterName] = listOf(page.toString()) })
                    )
                    .toString()
            ) {
                content()
            }
        }
    }

    Ul(attrs = {
        classes("breadcrumb")
        style {
            textAlign("center")
        }
    }) {
        if (currentPage > 1) Li { PageLink(currentPage - 1) { Text("<- Previous") } }
        var lastPage: Int? = null
        ((1..2) + (currentPage - 2..currentPage + 2) + (pageCount - 1..pageCount))
            .toSet()
            .sorted()
            .filter { it in 1..pageCount }
            .forEach { page ->
                if (lastPage != null && lastPage != page - 1) {
                    Li { Text("...") }
                }
                Li { PageLink(page) { Text("$page ") } }
                lastPage = page
            }
        if (currentPage < pageCount) Li { PageLink(currentPage + 1) { Text("Next ->") } }
    }
}