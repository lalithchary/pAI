package com.pai.personalai.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.pai.personalai.domain.model.ServerConfig
import com.pai.personalai.domain.model.ThemeMode
import com.pai.personalai.domain.repository.ConfigRepository
import com.pai.personalai.util.SecurePreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class ConfigRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val securePreferences: SecurePreferences
) : ConfigRepository {

    companion object {
        private val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
    }

    override fun getConfig(): ServerConfig? {
        return securePreferences.getConfig()
    }

    override fun saveConfig(config: ServerConfig) {
        securePreferences.saveConfig(config)
    }

    override fun clearConfig() {
        securePreferences.clearConfig()
    }

    override fun isLoggedIn(): Boolean {
        return securePreferences.isLoggedIn()
    }

    override fun setLoggedIn(value: Boolean) {
        securePreferences.setLoggedIn(value)
    }

    override fun saveEmail(email: String) {
        securePreferences.saveEmail(email)
    }

    override fun getEmail(): String? {
        return securePreferences.getEmail()
    }

    override fun clearAll() {
        securePreferences.clearAll()
    }

    override fun getThemeMode(): Flow<ThemeMode> {
        return context.dataStore.data.map { preferences ->
            val value = preferences[THEME_MODE_KEY] ?: ThemeMode.SYSTEM.name
            try {
                ThemeMode.valueOf(value)
            } catch (e: Exception) {
                ThemeMode.SYSTEM
            }
        }
    }

    override suspend fun setThemeMode(mode: ThemeMode) {
        context.dataStore.edit { preferences ->
            preferences[THEME_MODE_KEY] = mode.name
        }
    }
}
