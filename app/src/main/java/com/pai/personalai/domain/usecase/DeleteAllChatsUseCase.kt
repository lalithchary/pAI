package com.pai.personalai.domain.usecase

import com.pai.personalai.domain.repository.ChatRepository
import javax.inject.Inject

class DeleteAllChatsUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke() {
        chatRepository.deleteAllChats()
    }
}
