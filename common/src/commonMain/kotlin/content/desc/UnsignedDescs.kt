package argoner.common.content.desc

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("ubyte")
class UByteDesc(override val value: UByte) : Descriptor<UByte>()

@Serializable
@SerialName("ushort")
class UShortDesc(override val value: UShort) : Descriptor<UShort>()

@Serializable
@SerialName("uint")
class UIntDesc(override val value: UInt) : Descriptor<UInt>()

@Serializable
@SerialName("ulong")
class ULongDesc(override val value: ULong) : Descriptor<ULong>()