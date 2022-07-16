package argoner.common.content.desc

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

abstract class NumberDescriptor<T : Number> : Descriptor<T>()

@Serializable
@SerialName("byte")
class ByteDesc(override val value: Byte) : NumberDescriptor<Byte>()

@Serializable
@SerialName("short")
class ShortDesc(override val value: Short) : NumberDescriptor<Short>()

@Serializable
@SerialName("int")
class IntDesc(override val value: Int) : NumberDescriptor<Int>()

@Serializable
@SerialName("long")
class LongDesc(override val value: Long) : NumberDescriptor<Long>()

@Serializable
@SerialName("float")
class FloatDesc(override val value: Float) : NumberDescriptor<Float>()

@Serializable
@SerialName("double")
class DoubleDesc(override val value: Double) : NumberDescriptor<Double>()
