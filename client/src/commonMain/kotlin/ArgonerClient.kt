package argoner.client

import kotlinx.serialization.json.Json
import kotlin.reflect.KType
import kotlin.reflect.typeOf

expect class ArgonerClient(
    baseUrl: String,
    serializer: Json = ArgonerClients.defaultSerializer
) {

    val baseUrl: String
    val serializer: Json

    suspend fun <T : Any> call(
        path: String,
        method: String = "GET",
        data: Any? = null,
        queryParams: Map<String, String> = emptyMap(),
        responseType: KType
    ): T

}

suspend inline fun <reified T : Any> ArgonerClient.call(
    path: String,
    method: String = "GET",
    data: Any? = null,
    queryParams: Map<String, String> = emptyMap()
) =
    call<T>(path, method, data, queryParams, typeOf<T>())