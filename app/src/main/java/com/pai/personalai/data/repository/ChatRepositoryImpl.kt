package com.pai.personalai.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.pai.personalai.data.local.dao.ChatDao
import com.pai.personalai.data.local.dao.MessageDao
import com.pai.personalai.data.remote.api.AIApiService
import com.pai.personalai.data.remote.dto.ChatRequest
import com.pai.personalai.data.remote.dto.ContentPart
import com.pai.personalai.data.remote.dto.ImageUrl
import com.pai.personalai.data.remote.dto.MessageDto
import com.pai.personalai.domain.model.AIModel
import com.pai.personalai.domain.model.Chat
import com.pai.personalai.domain.model.Message
import com.pai.personalai.domain.model.MessageRole
import com.pai.personalai.domain.model.MessageStatus
import com.pai.personalai.domain.repository.ChatRepository
import com.pai.personalai.util.FileUtils
import com.pai.personalai.util.Resource
import com.pai.personalai.util.SecurePreferences
import com.pai.personalai.util.toDomain
import com.pai.personalai.util.toEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepositoryImpl @Inject constructor(
    private val chatDao: ChatDao,
    private val messageDao: MessageDao,
    private val apiService: AIApiService,
    private val securePreferences: SecurePreferences,
    @ApplicationContext private val context: Context
) : ChatRepository {

    override fun getChats(): Flow<List<Chat>> {
        return chatDao.getAllChats().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getMessages(chatId: String): Flow<List<Message>> {
        return messageDao.getMessagesByChatId(chatId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun sendMessage(
        chatId: String,
        content: String,
        model: String,
        attachments: List<Uri>
    ): Resource<Message> {
        Log.d("ChatRepository", "sendMessage: chatId=$chatId, model=$model, attachments=${attachments.size}")
        val userMessage = Message(
            id = UUID.randomUUID().toString(),
            chatId = chatId,
            content = content,
            role = MessageRole.USER,
            status = MessageStatus.SENDING
        )

        messageDao.insertMessage(userMessage.toEntity())

        return try {
            val messages = mutableListOf<MessageDto>()

            val messageContent: Any = if (attachments.isEmpty()) {
                content
            } else {
                val parts = mutableListOf<ContentPart>()
                if (content.isNotBlank()) {
                    parts.add(ContentPart(type = "text", text = content))
                }
                attachments.forEach { uri ->
                    val mimeType = FileUtils.getMimeType(context, uri)
                    if (mimeType?.startsWith("image/") == true) {
                        val base64 = FileUtils.uriToBase64(context, uri)
                        if (base64 != null) {
                            parts.add(
                                ContentPart(
                                    type = "image_url",
                                    imageUrl = ImageUrl(url = "data:$mimeType;base64,$base64")
                                )
                            )
                        }
                    }
                }
                parts
            }

            messages.add(MessageDto(role = "user", content = messageContent))

            val request = ChatRequest(
                model = model,
                messages = messages,
                stream = false,
                maxTokens = if (attachments.isNotEmpty()) 1000 else null
            )

            val response = apiService.chatCompletions(request)

            if (response.error != null) {
                Log.e("ChatRepository", "API Error: ${response.error.message}")
                val failedUserMessage = userMessage.copy(status = MessageStatus.FAILED)
                messageDao.updateMessage(failedUserMessage.toEntity())
                return Resource.Error(response.error.message ?: "Unknown error")
            }

            // Update user message status to SENT
            messageDao.updateMessage(userMessage.copy(status = MessageStatus.SENT).toEntity())

            val aiContent = response.choices?.firstOrNull()?.message?.content?.toString() ?: ""

            val aiMessage = Message(
                id = UUID.randomUUID().toString(),
                chatId = chatId,
                content = aiContent,
                role = MessageRole.ASSISTANT,
                status = MessageStatus.SENT
            )

            messageDao.insertMessage(aiMessage.toEntity())

            val lastMessagePreview = if (aiContent.length > 100) aiContent.take(100) + "..." else aiContent
            val chat = chatDao.getChatById(chatId)
            if (chat != null) {
                chatDao.updateChat(
                    chat.copy(
                        lastMessage = lastMessagePreview,
                        timestamp = System.currentTimeMillis(),
                        messageCount = chat.messageCount + 2
                    )
                )
            }

            Resource.Success(aiMessage)
        } catch (e: Exception) {
            Log.e("ChatRepository", "sendMessage Exception", e)
            val failedUserMessage = userMessage.copy(status = MessageStatus.FAILED)
            messageDao.updateMessage(failedUserMessage.toEntity())
            Resource.Error(e.message ?: "Network error occurred", e)
        }
    }

    override suspend fun createChat(title: String): Chat {
        val chat = Chat(
            id = UUID.randomUUID().toString(),
            title = title,
            timestamp = System.currentTimeMillis()
        )
        chatDao.insertChat(chat.toEntity())
        return chat
    }

    override suspend fun deleteChat(chatId: String) {
        messageDao.deleteMessagesByChatId(chatId)
        chatDao.deleteChat(chatId)
    }

    override suspend fun deleteAllChats() {
        messageDao.deleteAllMessages()
        chatDao.deleteAllChats()
    }

    override suspend fun deleteMessages(chatId: String) {
        messageDao.deleteMessagesByChatId(chatId)
    }

    override suspend fun getAvailableModels(): Resource<List<AIModel>> {
        return try {
            val response = apiService.getModels()
            if (response.error != null) {
                return Resource.Error(response.error.message ?: "Failed to fetch models")
            }
            val models = response.data?.map { it.toDomain() } ?: emptyList()
            Resource.Success(models)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to fetch models", e)
        }
    }

    override suspend fun testConnection(): Resource<Boolean> {
        return try {
            val response = apiService.getModels()
            if (response.error != null) {
                Resource.Error(response.error.message ?: "Connection failed")
            } else {
                Resource.Success(true)
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Connection failed", e)
        }
    }
}
