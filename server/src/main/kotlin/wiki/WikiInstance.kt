package argoner.server.wiki

import argoner.common.content.wiki.WikiID
import argoner.common.content.wiki.WikiInstallation
import argoner.server.util.HttpUserAgentInterceptor
import argoner.server.util.component.ComponentContainer
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient

class WikiInstance(val config: WikiConfig) : ComponentContainer<WikiInstance>() {

    val id: WikiID get() = config.id
    val installation = WikiInstallation(id = id, name = config.name, url = config.url)

    val httpClient = OkHttpClient.Builder()
        .proxy(config.proxy?.toJavaProxy())
        .addInterceptor(HttpUserAgentInterceptor)
        .build()

    val apiBaseUrl = config.apiUrl.toHttpUrl()

}