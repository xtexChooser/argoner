package argoner.server.util.component

import argoner.common.util.Identifier
import java.util.*

abstract class ComponentContainer<T : ComponentContainer<T>> {

    companion object CompanionComponents :
        ArrayList<CompanionComponentProvider>(ServiceLoader.load(CompanionComponentProvider::class.java).toSet())

    val extensions: Map<Identifier, Component<T>> = mutableMapOf()

    init {
        forEach { it(this) }
    }

    operator fun get(key: Identifier) =
        extensions[key] ?: error("$key not present in ${this::class.qualifiedName}($this)")

    operator fun <E : Component<T>> get(type: ComponentType<T, E>) =
        getOrCompute(type.key, type.ctor)

    @Suppress("UNCHECKED_CAST")
    private fun <C: Component<T>> getOrCompute(key: Identifier, ctor: T.() -> C): C =
        (extensions as MutableMap<Identifier, C>).getOrPut(key) { (this as T).ctor() }

    operator fun set(key: Identifier, component: Component<T>) {
        (extensions as MutableMap<Identifier, Component<T>>)[key] = component
    }

    operator fun contains(key: Identifier) = key in extensions

}