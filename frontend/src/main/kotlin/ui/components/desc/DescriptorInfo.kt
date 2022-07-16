package argoner.frontend.web.ui.components.desc

import androidx.compose.runtime.Composable
import argoner.common.content.desc.*
import org.jetbrains.compose.web.dom.Text

@Composable
fun DescriptorInfo(desc: AnyDescriptor) = when (desc) {
    else -> Text(desc.value.toString())
}