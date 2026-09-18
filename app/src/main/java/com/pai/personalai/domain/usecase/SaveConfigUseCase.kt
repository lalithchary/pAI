package com.pai.personalai.domain.usecase

import com.pai.personalai.domain.model.ServerConfig
import com.pai.personalai.domain.repository.ConfigRepository
import javax.inject.Inject

class SaveConfigUseCase @Inject constructor(
    private val configRepository: ConfigRepository
) {
    operator fun invoke(config: ServerConfig) {
        configRepository.saveConfig(config.copy(configured = true))
    }
}
