package com.pai.personalai.domain.usecase

import com.pai.personalai.domain.model.Message
import com.pai.personalai.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChatHistoryUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(chatId: String): Flow<List<Message>> {
        return chatRepository.getMessages(chatId)
    }
}
