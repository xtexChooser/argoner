package argoner.server.util

import argoner.server.ArgonerServer
import okhttp3.Interceptor

object HttpUserAgentInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain) = chain.proceed(
        chain.request()
            .newBuilder()
            .header("User-Agent", ArgonerServer.config.userAgent)
            .build()
    )

}