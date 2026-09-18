package com.pai.personalai.data.remote.interceptor

import com.pai.personalai.util.SecurePreferences
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val securePreferences: SecurePreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val config = securePreferences.getConfig()

        if (config?.apiKey.isNullOrBlank()) {
            return chain.proceed(originalRequest)
        }

        val authenticatedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer ${config!!.apiKey}")
            .header("Content-Type", "application/json")
            .build()

        return chain.proceed(authenticatedRequest)
    }
}
