package argoner.frontend.web.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.builders.InputAttrsScope
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLHeadingElement
import org.w3c.dom.HTMLParagraphElement

@Composable
fun ModalDialog(
    id: String,
    opened: State<Boolean>,
    title: ContentBuilder<HTMLHeadingElement>? = null,
    subtitle: ContentBuilder<HTMLHeadingElement>? = null,
    body: ContentBuilder<HTMLDivElement>? = null,
    withCloseButton: Boolean = true,
    inputBox: (InputAttrsScope<Boolean>.() -> Unit)? = null,
    onClose: (() -> Unit)? = null,
    content: ContentBuilder<HTMLParagraphElement>?,
) {
    Input(type = InputType.Checkbox, attrs = {
        classes("modal-state")
        id(id)
        if (inputBox != null)
            inputBox()
        if (opened.value)
            defaultChecked()
        if (onClose != null) {
            onChange {
                if (!it.value) onClose()
            }
        }
    })
    Div(attrs = { classes("modal") }) {
        Label(forId = id, attrs = { classes("modal-bg") })
        Div(attrs = { classes("modal-body") }) {
            if (withCloseButton)
                Label(forId = id, attrs = { classes("btn-close") }) { Text("X") }
            if (title != null)
                H4(attrs = { classes("modal-title") }, content = title)
            if (subtitle != null)
                H5(attrs = { classes("modal-subtitle") }, content = subtitle)
            if (content != null)
                P(attrs = { classes("modal-text") }, content = content)
            if (body != null)
                body()
        }
    }
}