package argoner.common.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(Identifier.Serializer::class)
data class Identifier(
    val namespace: String,
    val path: String,
) {

    constructor(descriptor: String) : this(
        namespace = descriptor.substringBefore(':', "argoner"),
        path = descriptor.substringAfter(':'),
    )

    override fun toString() = "$namespace:$path"

    object Serializer : KSerializer<Identifier> {

        override val descriptor = PrimitiveSerialDescriptor("Identifier", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: Identifier) {
            encoder.encodeString(value.toString())
        }

        override fun deserialize(decoder: Decoder) = Identifier(decoder.decodeString())

    }

}
