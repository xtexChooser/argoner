package argoner.server.issue.type

import argoner.common.util.Identifier
import argoner.server.issue.details.IssueDetails
import argoner.server.module.Module
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

class IssueType<T : IssueDetails>(
    val module: Module,
    val id: Identifier,
    val detailsType: KClass<T>,
    val detailsSerializer: KSerializer<T> = @OptIn(InternalSerializationApi::class) detailsType.serializer(),
    val name: String,
) {

    companion object List : ArrayList<IssueType<*>>() {
        operator fun get(id: Identifier) = find { it.id == id }
    }

    init {
        @OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
        require(detailsType.serializer().descriptor.serialName == id.toString()) { "Issue details for type $id's serial name in $module does not match the type's ID, add @SerialName(\"$id\") to ${detailsType.qualifiedName}" }
        require(id.namespace == module.namespace) { "The namespace of issue type $id 's ID does not match $module 's namespace" }
    }

}