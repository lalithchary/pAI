package com.pai.personalai.domain.repository

import android.net.Uri
import com.pai.personalai.domain.model.AIModel
import com.pai.personalai.domain.model.Chat
import com.pai.personalai.domain.model.Message
import com.pai.personalai.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getChats(): Flow<List<Chat>>
    fun getMessages(chatId: String): Flow<List<Message>>
    suspend fun sendMessage(
        chatId: String,
        content: String,
        model: String,
        attachments: List<Uri> = emptyList()
    ): Resource<Message>
    suspend fun createChat(title: String = "New Chat"): Chat
    suspend fun deleteChat(chatId: String)
    suspend fun deleteAllChats()
    suspend fun deleteMessages(chatId: String)
    suspend fun getAvailableModels(): Resource<List<AIModel>>
    suspend fun testConnection(): Resource<Boolean>
}
