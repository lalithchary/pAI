package com.pai.personalai.domain.model

data class ServerConfig(
    val baseUrl: String = "",
    val apiKey: String = "",
    val selectedModel: String = "",
    val configured: Boolean = false
)
