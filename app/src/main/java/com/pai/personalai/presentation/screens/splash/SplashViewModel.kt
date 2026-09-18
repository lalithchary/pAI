package com.pai.personalai.presentation.screens.splash

import androidx.lifecycle.ViewModel
import com.pai.personalai.domain.repository.ConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val configRepository: ConfigRepository
) : ViewModel() {

    fun isServerConfigured(): Boolean {
        val config = configRepository.getConfig()
        return config?.configured == true && config.baseUrl.isNotBlank()
    }
}
