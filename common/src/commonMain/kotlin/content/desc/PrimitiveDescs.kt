package argoner.common.content.desc

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("string")
class StringDesc(override val value: String) : Descriptor<String>()

@Serializable
@SerialName("long")
class LongDesc(override val value: Long) : Descriptor<Long>()

@Serializable
@SerialName("double")
class DoubleDesc(override val value: Double) : Descriptor<Double>()

@Serializable
@SerialName("boolean")
class BooleanDesc(override val value: Boolean) : Descriptor<Boolean>()
