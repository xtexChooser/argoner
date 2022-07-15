package argoner.server.module

import argoner.common.util.Identifier
import argoner.server.issue.details.IssueDetails
import argoner.server.issue.type.IssueType
import argoner.server.util.component.CompanionComponentProvider
import argoner.server.util.component.ComponentContainer
import argoner.server.util.component.ComponentType
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

@Suppress("FunctionName")
class ContentRegisterer(private val module: Module) {

    fun <T : ComponentContainer<T>, E : ComponentType<T, *>> CompanionComponent(target: KClass<T>, type: E) {
        ComponentContainer += object : CompanionComponentProvider {
            override fun invoke(container: ComponentContainer<*>) {
                if (target.isInstance(container))
                    @Suppress("UNCHECKED_CAST")
                    (container as T)[type]
            }
        }
    }

    fun <T : IssueDetails> IssueType(id: Identifier, detailsType: KClass<T>) =
        IssueType(module, id, detailsType).also { IssueType += it }

    fun <T : IssueDetails> IssueType(detailsType: KClass<T>) =
        @OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
        IssueType(Identifier(detailsType.serializer().descriptor.serialName), detailsType)

    fun Identifier(path: String) = module.identity(path)

}