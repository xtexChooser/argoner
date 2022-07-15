package argoner.common.content.desc

import kotlinx.serialization.Contextual
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable

@Serializable
@Polymorphic
sealed class Descriptor<T : Any> {

    @Contextual
    abstract val value: T

    fun toNumber() = value as Number

    override fun hashCode() = value.hashCode()
    override fun equals(other: Any?) = ((other != null)
            && (other is Descriptor<*>)
            && (other.value == value))

    override fun toString() = "Descriptor($value)"

}

typealias AnyDescriptor = Descriptor<out @Contextual Any>
