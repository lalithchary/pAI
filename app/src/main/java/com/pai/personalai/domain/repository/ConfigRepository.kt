package com.pai.personalai.domain.repository

import com.pai.personalai.domain.model.ServerConfig
import com.pai.personalai.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow

interface ConfigRepository {
    fun getConfig(): ServerConfig?
    fun saveConfig(config: ServerConfig)
    fun clearConfig()
    fun isLoggedIn(): Boolean
    fun setLoggedIn(value: Boolean)
    fun saveEmail(email: String)
    fun getEmail(): String?
    fun clearAll()
    fun getThemeMode(): Flow<ThemeMode>
    suspend fun setThemeMode(mode: ThemeMode)
}
