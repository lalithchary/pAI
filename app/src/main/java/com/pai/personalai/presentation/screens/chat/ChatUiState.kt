package com.pai.personalai.presentation.screens.chat

import android.net.Uri
import com.pai.personalai.domain.model.Chat
import com.pai.personalai.domain.model.Message

data class Attachment(
    val uri: Uri,
    val name: String = "",
    val size: String = "",
    val type: String = "",
    val isUploading: Boolean = false
)

data class ChatUiState(
    val currentChatId: String? = null,
    val chats: List<Chat> = emptyList(),
    val messages: List<Message> = emptyList(),
    val inputText: String = "",
    val attachedFiles: List<Attachment> = emptyList(),
    val isSending: Boolean = false,
    val isTyping: Boolean = false,
    val selectedModel: String = "",
    val error: String? = null,
    val showAttachmentSheet: Boolean = false
)
