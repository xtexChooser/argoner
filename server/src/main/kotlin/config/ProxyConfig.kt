package argoner.server.config

import kotlinx.serialization.Serializable
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Proxy as JavaProxy

@Serializable
data class ProxyConfig(
    val type: Type,
    val host: String,
    val port: Int
) {

    fun toJavaProxy() = JavaProxy(type.javaType, InetSocketAddress(InetAddress.getByName(host), port))

    enum class Type(val javaType: JavaProxy.Type) {

        SOCKS(JavaProxy.Type.SOCKS),
        HTTP(JavaProxy.Type.HTTP),

    }

}
