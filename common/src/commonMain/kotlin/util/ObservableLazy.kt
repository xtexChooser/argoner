package argoner.common.util

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

inline fun <T> observableLazy(
    noinline provider: () -> T,
    crossinline onChange: (property: KProperty<*>, oldValue: T?, newValue: T) -> Unit
): ReadWriteProperty<Any, T> =
    object : ReadWriteProperty<Any, T> {
        private var lazy: Lazy<T> = lazy(provider)

        override fun getValue(thisRef: Any, property: KProperty<*>) = lazy.getValue(thisRef, property)

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            val oldValue = if (lazy.isInitialized()) lazy.value else null
            lazy = lazyOf(value)
            onChange(property, oldValue, value)
        }
    }