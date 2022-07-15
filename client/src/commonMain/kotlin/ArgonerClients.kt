package argoner.client

import kotlinx.serialization.json.Json

object ArgonerClients {

    val defaultSerializer = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

}