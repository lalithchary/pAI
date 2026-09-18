package com.pai.personalai.domain.model

data class Message(
    val id: String,
    val chatId: String,
    val content: String,
    val role: MessageRole,
    val timestamp: Long = System.currentTimeMillis(),
    val attachments: List<Attachment> = emptyList(),
    val status: MessageStatus = MessageStatus.SENT
)
