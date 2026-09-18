package com.pai.personalai.domain.usecase

import android.net.Uri
import com.pai.personalai.domain.model.Message
import com.pai.personalai.domain.repository.ChatRepository
import com.pai.personalai.util.Resource
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(
        chatId: String,
        content: String,
        model: String,
        attachments: List<Uri> = emptyList()
    ): Resource<Message> {
        if (content.isBlank() && attachments.isEmpty()) {
            return Resource.Error("Message cannot be empty")
        }
        return chatRepository.sendMessage(chatId, content, model, attachments)
    }
}
