package argoner.common.content.desc

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("string")
class StringDesc(override val value: String) : Descriptor<String>()

@Serializable
@SerialName("boolean")
class BooleanDesc(override val value: Boolean) : Descriptor<Boolean>()
