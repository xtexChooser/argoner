package argoner.server.module

import argoner.common.util.Identifier

abstract class Module {

    abstract val namespace: String

    fun identity(path: String) = if (':' in path) Identifier(path) else Identifier(namespace, path)

    open fun init() {
        with(ContentRegisterer(this)) { register() }
    }

    protected open fun ContentRegisterer.register() {
    }

    override fun equals(other: Any?) = other === this
    override fun hashCode() = namespace.hashCode() * 31 + 0xae42
    override fun toString() = "Module($namespace)"

}