package com.pai.personalai.data.remote.interceptor

import com.pai.personalai.di.RetrofitBaseUrlProvider
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseUrlInterceptor @Inject constructor(
    private val baseUrlProvider: RetrofitBaseUrlProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val newBaseUrl = baseUrlProvider.getBaseUrl().toHttpUrlOrNull()

        if (newBaseUrl != null) {
            val newUrl = request.url.newBuilder()
                .scheme(newBaseUrl.scheme)
                .host(newBaseUrl.host)
                .port(newBaseUrl.port)
                .build()
            request = request.newBuilder()
                .url(newUrl)
                .build()
        }

        return chain.proceed(request)
    }
}
