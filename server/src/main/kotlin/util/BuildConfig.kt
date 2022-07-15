package argoner.server.util

object BuildConfig {

    @JvmField
    val VERSION: String = javaClass.`package`.implementationVersion ?: "DEV"

}