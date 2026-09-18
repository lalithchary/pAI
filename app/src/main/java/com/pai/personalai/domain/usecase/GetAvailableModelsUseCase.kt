package com.pai.personalai.domain.usecase

import com.pai.personalai.domain.model.AIModel
import com.pai.personalai.domain.repository.ChatRepository
import com.pai.personalai.util.Resource
import javax.inject.Inject

class GetAvailableModelsUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(): Resource<List<AIModel>> {
        return chatRepository.getAvailableModels()
    }
}
