package com.pai.personalai.domain.usecase

import com.pai.personalai.domain.repository.ChatRepository
import javax.inject.Inject

class DeleteChatUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatId: String) {
        chatRepository.deleteChat(chatId)
    }
}
