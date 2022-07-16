package argoner.frontend.web.ui.components.desc

import androidx.compose.runtime.Composable
import argoner.common.content.desc.AnyDescriptor
import argoner.common.util.Identifier
import org.jetbrains.compose.web.dom.*

@Composable
fun DescriptorsInfo(descs: Map<Identifier, AnyDescriptor>) {
    Table {
        Tbody {
            descs.forEach { (id, desc) ->
                Tr {
                    Td { Text(id.toString()) }
                    Td { DescriptorInfo(desc) }
                }
            }
        }
    }
}