package argoner.frontend.web.ui.components.papercss

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.name
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLLabelElement
import kotlin.random.Random

@Composable
fun Collapsible(
    id: String = remember { derivedStateOf { "collapsible-${Random.nextLong()}" } }.value,
    title: ContentBuilder<HTMLLabelElement>,
    attrs: AttrBuilderContext<HTMLDivElement>? = null,
    body: ContentBuilder<HTMLDivElement>,
) {
    Div(attrs = { classes("collapsible"); attrs?.invoke(this) }) {
        Input(type = InputType.Checkbox, attrs = {
            id(id)
            name("collapsible")
        })
        Label(forId = id, content = title)
        Div(attrs = { classes("collapsible-body") }, content = body)
    }
}