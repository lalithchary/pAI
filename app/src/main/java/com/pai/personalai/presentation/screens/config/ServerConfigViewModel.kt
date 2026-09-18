package com.pai.personalai.presentation.screens.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pai.personalai.domain.model.ServerConfig
import com.pai.personalai.domain.repository.ConfigRepository
import com.pai.personalai.domain.usecase.GetAvailableModelsUseCase
import com.pai.personalai.domain.usecase.SaveConfigUseCase
import com.pai.personalai.domain.usecase.TestConnectionUseCase
import com.pai.personalai.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServerConfigViewModel @Inject constructor(
    private val configRepository: ConfigRepository,
    private val saveConfigUseCase: SaveConfigUseCase,
    private val getAvailableModelsUseCase: GetAvailableModelsUseCase,
    private val testConnectionUseCase: TestConnectionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConfigUiState())
    val uiState: StateFlow<ConfigUiState> = _uiState.asStateFlow()

    init {
        loadExistingConfig()
    }

    private fun loadExistingConfig() {
        configRepository.getConfig()?.let { config ->
            _uiState.update {
                it.copy(
                    baseUrl = config.baseUrl,
                    apiKey = config.apiKey,
                    selectedModel = config.selectedModel
                )
            }
            if (config.baseUrl.isNotBlank() && config.apiKey.isNotBlank()) {
                fetchModels()
            }
        }
    }

    fun onBaseUrlChange(baseUrl: String) {
        _uiState.update { it.copy(baseUrl = baseUrl, error = null, testSuccess = null) }
    }

    fun onApiKeyChange(apiKey: String) {
        _uiState.update { it.copy(apiKey = apiKey, error = null, testSuccess = null) }
    }

    fun onModelChange(model: String) {
        _uiState.update { it.copy(selectedModel = model) }
    }

    fun testConnection() {
        val state = _uiState.value
        if (state.baseUrl.isBlank() || state.apiKey.isBlank()) {
            _uiState.update { it.copy(error = "Please enter base URL and API key") }
            return
        }

        _uiState.update { it.copy(isTesting = true, testSuccess = null, error = null) }

        viewModelScope.launch {
            saveConfigUseCase(
                ServerConfig(
                    baseUrl = state.baseUrl.trim(),
                    apiKey = state.apiKey.trim(),
                    selectedModel = state.selectedModel
                )
            )

            when (val result = testConnectionUseCase()) {
                is Resource.Success -> {
                    _uiState.update { it.copy(isTesting = false, testSuccess = true) }
                    fetchModels()
                }
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isTesting = false,
                            testSuccess = false,
                            error = result.message
                        )
                    }
                }
                is Resource.Loading -> {}
            }
        }
    }

    fun fetchModels() {
        _uiState.update { it.copy(isFetchingModels = true, error = null) }

        viewModelScope.launch {
            when (val result = getAvailableModelsUseCase()) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isFetchingModels = false,
                            models = result.data,
                            selectedModel = it.selectedModel.ifBlank {
                                result.data.firstOrNull()?.id ?: ""
                            }
                        )
                    }
                }
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isFetchingModels = false,
                            error = result.message
                        )
                    }
                }
                is Resource.Loading -> {}
            }
        }
    }

    fun saveConfig() {
        val state = _uiState.value
        if (state.baseUrl.isBlank() || state.apiKey.isBlank() || state.selectedModel.isBlank()) {
            _uiState.update { it.copy(error = "Please fill all fields and fetch models") }
            return
        }

        _uiState.update { it.copy(isSaving = true, error = null) }

        viewModelScope.launch {
            saveConfigUseCase(
                ServerConfig(
                    baseUrl = state.baseUrl.trim(),
                    apiKey = state.apiKey.trim(),
                    selectedModel = state.selectedModel,
                    configured = true
                )
            )
            _uiState.update { it.copy(isSaving = false, saved = true) }
        }
    }

    fun resetSaved() {
        _uiState.update { it.copy(saved = false) }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
