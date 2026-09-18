package com.pai.personalai.viewmodel

import com.pai.personalai.domain.model.AIModel
import com.pai.personalai.domain.model.ServerConfig
import com.pai.personalai.domain.repository.ChatRepository
import com.pai.personalai.domain.repository.ConfigRepository
import com.pai.personalai.domain.usecase.GetAvailableModelsUseCase
import com.pai.personalai.domain.usecase.SaveConfigUseCase
import com.pai.personalai.domain.usecase.TestConnectionUseCase
import com.pai.personalai.presentation.screens.config.ServerConfigViewModel
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
class ConfigViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: ServerConfigViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        val configRepository = object : ConfigRepository {
            private var config: ServerConfig? = null
            override fun getConfig() = config
            override fun saveConfig(cfg: ServerConfig) { config = cfg }
            override fun clearConfig() { config = null }
            override fun isLoggedIn() = true
            override fun setLoggedIn(value: Boolean) {}
            override fun saveEmail(email: String) {}
            override fun getEmail() = "test@test.com"
            override fun clearAll() {}
            override fun getThemeMode() = flowOf(com.pai.personalai.domain.model.ThemeMode.SYSTEM)
            override suspend fun setThemeMode(mode: com.pai.personalai.domain.model.ThemeMode) {}
        }

        val chatRepository = object : ChatRepository {
            override fun getChats() = flowOf(emptyList<com.pai.personalai.domain.model.Chat>())
            override fun getMessages(chatId: String) = flowOf(emptyList())
            override suspend fun sendMessage(chatId: String, content: String, model: String) = Resource.Success(com.pai.personalai.domain.model.Message(id = "1", chatId = chatId, content = content, role = com.pai.personalai.domain.model.MessageRole.ASSISTANT))
            override suspend fun createChat(title: String) = com.pai.personalai.domain.model.Chat(id = "1", title = title)
            override suspend fun deleteChat(chatId: String) {}
            override suspend fun deleteAllChats() {}
            override suspend fun deleteMessages(chatId: String) {}
            override suspend fun getAvailableModels() = Resource.Success(
                listOf(AIModel(id = "gpt-4", name = "GPT-4", ownedBy = "openai"))
            )
            override suspend fun testConnection() = Resource.Success(true)
        }

        viewModel = ServerConfigViewModel(
            saveConfigUseCase = SaveConfigUseCase(configRepository),
            getAvailableModelsUseCase = GetAvailableModelsUseCase(chatRepository),
            testConnectionUseCase = TestConnectionUseCase(chatRepository)
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is empty`() {
        val state = viewModel.uiState.value
        assertEquals("", state.baseUrl)
        assertEquals("", state.apiKey)
        assertEquals("", state.selectedModel)
    }

    @Test
    fun `onBaseUrlChange updates state`() {
        viewModel.onBaseUrlChange("https://api.test.com")
        assertEquals("https://api.test.com", viewModel.uiState.value.baseUrl)
    }

    @Test
    fun `onApiKeyChange updates state`() {
        viewModel.onApiKeyChange("sk-test")
        assertEquals("sk-test", viewModel.uiState.value.apiKey)
    }

    @Test
    fun `fetchModels updates models list`() {
        viewModel.fetchModels()
        val state = viewModel.uiState.value
        assertTrue(state.models.isNotEmpty())
        assertEquals("gpt-4", state.models.first().id)
    }
}
