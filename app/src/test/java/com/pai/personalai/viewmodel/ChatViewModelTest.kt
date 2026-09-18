package com.pai.personalai.viewmodel

import com.pai.personalai.domain.model.Chat
import com.pai.personalai.domain.model.Message
import com.pai.personalai.domain.model.MessageRole
import com.pai.personalai.domain.repository.ChatRepository
import com.pai.personalai.domain.repository.ConfigRepository
import com.pai.personalai.domain.usecase.CreateChatUseCase
import com.pai.personalai.domain.usecase.DeleteChatUseCase
import com.pai.personalai.domain.usecase.SendMessageUseCase
import com.pai.personalai.presentation.screens.chat.ChatViewModel
import com.pai.personalai.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ChatViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: ChatViewModel
    private lateinit var chatRepository: ChatRepository
    private lateinit var configRepository: ConfigRepository
    private lateinit var sendMessageUseCase: SendMessageUseCase
    private lateinit var createChatUseCase: CreateChatUseCase
    private lateinit var deleteChatUseCase: DeleteChatUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        chatRepository = object : ChatRepository {
            private val chats = mutableListOf<Chat>()
            private val messages = mutableListOf<Message>()

            override fun getChats() = flowOf(chats.toList())
            override fun getMessages(chatId: String) = flowOf(
                messages.filter { it.chatId == chatId }
            )
            override suspend fun sendMessage(
                chatId: String,
                content: String,
                model: String
            ): Resource<Message> {
                val msg = Message(
                    id = "ai-1",
                    chatId = chatId,
                    content = "AI response",
                    role = MessageRole.ASSISTANT
                )
                messages.add(msg)
                return Resource.Success(msg)
            }
            override suspend fun createChat(title: String): Chat {
                val chat = Chat(id = "chat-1", title = title)
                chats.add(chat)
                return chat
            }
            override suspend fun deleteChat(chatId: String) { chats.removeAll { it.id == chatId } }
            override suspend fun deleteAllChats() { chats.clear() }
            override suspend fun deleteMessages(chatId: String) { messages.removeAll { it.chatId == chatId } }
            override suspend fun getAvailableModels() = Resource.Success(emptyList<com.pai.personalai.domain.model.AIModel>())
            override suspend fun testConnection() = Resource.Success(true)
        }

        configRepository = object : ConfigRepository {
            override fun getConfig() = null
            override fun saveConfig(config: com.pai.personalai.domain.model.ServerConfig) {}
            override fun clearConfig() {}
            override fun isLoggedIn() = true
            override fun setLoggedIn(value: Boolean) {}
            override fun saveEmail(email: String) {}
            override fun getEmail() = "test@test.com"
            override fun clearAll() {}
            override fun getThemeMode() = flowOf(com.pai.personalai.domain.model.ThemeMode.SYSTEM)
            override suspend fun setThemeMode(mode: com.pai.personalai.domain.model.ThemeMode) {}
        }

        sendMessageUseCase = SendMessageUseCase(chatRepository)
        createChatUseCase = CreateChatUseCase(chatRepository)
        deleteChatUseCase = DeleteChatUseCase(chatRepository)

        viewModel = ChatViewModel(
            chatRepository = chatRepository,
            configRepository = configRepository,
            sendMessageUseCase = sendMessageUseCase,
            createChatUseCase = createChatUseCase,
            deleteChatUseCase = deleteChatUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state has empty messages`() {
        val state = viewModel.uiState.value
        assertTrue(state.messages.isEmpty())
    }

    @Test
    fun `startNewChat creates a new chat`() {
        viewModel.startNewChat()
        val state = viewModel.uiState.value
        assertEquals("chat-1", state.currentChatId)
    }

    @Test
    fun `onInputChange updates input text`() {
        viewModel.onInputChange("Hello")
        assertEquals("Hello", viewModel.uiState.value.inputText)
    }

    @Test
    fun `sendMessage clears input and sends`() {
        viewModel.startNewChat()
        viewModel.onInputChange("Hello AI")
        viewModel.sendMessage()
        val state = viewModel.uiState.value
        assertEquals("", state.inputText)
    }
}
