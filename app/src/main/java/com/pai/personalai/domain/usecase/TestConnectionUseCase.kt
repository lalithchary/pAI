package com.pai.personalai.domain.usecase

import com.pai.personalai.domain.repository.ChatRepository
import com.pai.personalai.util.Resource
import javax.inject.Inject

class TestConnectionUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(): Resource<Boolean> {
        return chatRepository.testConnection()
    }
}
