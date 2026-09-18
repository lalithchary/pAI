package com.pai.personalai.domain.usecase

import com.pai.personalai.domain.model.Chat
import com.pai.personalai.domain.repository.ChatRepository
import javax.inject.Inject

class CreateChatUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(title: String = "New Chat"): Chat {
        return chatRepository.createChat(title)
    }
}
