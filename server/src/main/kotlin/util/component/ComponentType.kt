package argoner.server.util.component

import argoner.common.util.Identifier

open class ComponentType<E : ComponentContainer<E>, T : Component<E>>
    (val key: Identifier, val ctor: E.() -> T) {

    override fun equals(other: Any?) =
        this === other || (other is ComponentType<*, *> && key == other.key && ctor == other.ctor)

    override fun hashCode() = 31 * key.hashCode() + ctor.hashCode()

    override fun toString() = "ComponentType($key)"

}