package com.pai.personalai.di

import com.pai.personalai.util.Constants
import com.pai.personalai.util.SecurePreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitBaseUrlProvider @Inject constructor(
    private val securePreferences: SecurePreferences
) {
    fun getBaseUrl(): String {
        val config = securePreferences.getConfig()
        var url = config?.baseUrl ?: Constants.DEFAULT_BASE_URL
        if (!url.endsWith("/")) {
            url = "$url/"
        }
        return url
    }
}
