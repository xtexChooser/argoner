package argoner.client

import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.w3c.fetch.*
import kotlin.reflect.KType

actual class ArgonerClient actual constructor(actual val baseUrl: String, actual val serializer: Json) {

    actual suspend fun <T : Any> call(path: String, method: String, data: Any?, responseType: KType): T {
        val response = window.fetch(
            input = baseUrl + path,
            init = RequestInit(
                method = method,
                body = if (data == null) null else serializer.encodeToString(data),
                mode = RequestMode.SAME_ORIGIN,
                cache = RequestCache.NO_CACHE,
                redirect = RequestRedirect.FOLLOW
            )
        ).await()
        if (response.ok) {
            return serializer.decodeFromString(
                serializer.serializersModule.serializer(responseType),
                response.text().await()
            ).unsafeCast<T>()
        } else {
            error(
                "$method $path failed with ${response.type} ${response.status} " +
                        "${response.statusText}, response: $response"
            )
        }
    }

}