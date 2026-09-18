package com.pai.personalai.presentation.screens.config

import com.pai.personalai.domain.model.AIModel

data class ConfigUiState(
    val baseUrl: String = "",
    val apiKey: String = "",
    val selectedModel: String = "",
    val models: List<AIModel> = emptyList(),
    val isTesting: Boolean = false,
    val isFetchingModels: Boolean = false,
    val isSaving: Boolean = false,
    val testSuccess: Boolean? = null,
    val saved: Boolean = false,
    val error: String? = null
)
