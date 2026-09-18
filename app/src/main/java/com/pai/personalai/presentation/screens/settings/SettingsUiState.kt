package com.pai.personalai.presentation.screens.settings

import com.pai.personalai.domain.model.ThemeMode

data class SettingsUiState(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val serverUrl: String = "",
    val email: String = "",
    val showClearDialog: Boolean = false,
    val showLogoutDialog: Boolean = false
)
