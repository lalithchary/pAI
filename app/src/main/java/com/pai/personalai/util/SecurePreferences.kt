package com.pai.personalai.util

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.pai.personalai.domain.model.ServerConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecurePreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val encryptedPrefs: SharedPreferences = try {
        EncryptedSharedPreferences.create(
            context,
            Constants.SECURE_PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    } catch (e: Exception) {
        context.getSharedPreferences(Constants.SECURE_PREFS_NAME, Context.MODE_PRIVATE).edit().clear().apply()
        EncryptedSharedPreferences.create(
            context,
            Constants.SECURE_PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private val gson = Gson()

    fun saveConfig(config: ServerConfig) {
        val json = gson.toJson(config)
        encryptedPrefs.edit().putString("server_config", json).apply()
    }

    fun getConfig(): ServerConfig? {
        val json = encryptedPrefs.getString("server_config", null)
        return if (json != null) {
            try {
                gson.fromJson(json, ServerConfig::class.java)
            } catch (e: Exception) {
                null
            }
        } else null
    }

    fun clearConfig() {
        encryptedPrefs.edit().remove("server_config").apply()
    }

    fun isLoggedIn(): Boolean {
        return encryptedPrefs.getBoolean("logged_in", false)
    }

    fun setLoggedIn(value: Boolean) {
        encryptedPrefs.edit().putBoolean("logged_in", value).apply()
    }

    fun saveEmail(email: String) {
        encryptedPrefs.edit().putString("user_email", email).apply()
    }

    fun getEmail(): String? {
        return encryptedPrefs.getString("user_email", null)
    }

    fun clearAll() {
        encryptedPrefs.edit().clear().apply()
    }
}
