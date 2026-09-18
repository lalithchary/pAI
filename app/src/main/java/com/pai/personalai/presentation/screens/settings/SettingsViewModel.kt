package com.pai.personalai.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pai.personalai.domain.model.ThemeMode
import com.pai.personalai.domain.repository.ChatRepository
import com.pai.personalai.domain.repository.ConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val configRepository: ConfigRepository,
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        val config = configRepository.getConfig()
        val email = configRepository.getEmail() ?: ""

        viewModelScope.launch {
            configRepository.getThemeMode().collect { mode ->
                _uiState.update {
                    it.copy(
                        themeMode = mode,
                        serverUrl = config?.baseUrl ?: "",
                        email = email
                    )
                }
            }
        }
    }

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch {
            configRepository.setThemeMode(mode)
            _uiState.update { it.copy(themeMode = mode) }
        }
    }

    fun showClearHistoryDialog() {
        _uiState.update { it.copy(showClearDialog = true) }
    }

    fun hideClearHistoryDialog() {
        _uiState.update { it.copy(showClearDialog = false) }
    }

    fun clearChatHistory() {
        viewModelScope.launch {
            chatRepository.deleteAllChats()
            _uiState.update { it.copy(showClearDialog = false) }
        }
    }

    fun showLogoutDialog() {
        _uiState.update { it.copy(showLogoutDialog = true) }
    }

    fun hideLogoutDialog() {
        _uiState.update { it.copy(showLogoutDialog = false) }
    }

    fun logout() {
        configRepository.clearAll()
        _uiState.update { it.copy(showLogoutDialog = false) }
    }
}
