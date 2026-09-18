package com.pai.personalai.repository

import com.pai.personalai.data.local.dao.ChatDao
import com.pai.personalai.data.local.dao.MessageDao
import com.pai.personalai.data.local.entity.ChatEntity
import com.pai.personalai.data.local.entity.MessageEntity
import com.pai.personalai.data.remote.api.AIApiService
import com.pai.personalai.data.remote.dto.ChatResponse
import com.pai.personalai.data.remote.dto.Choice
import com.pai.personalai.data.remote.dto.MessageDto
import com.pai.personalai.data.repository.ChatRepositoryImpl
import com.pai.personalai.util.SecurePreferences
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ChatRepositoryTest {

    private lateinit var repository: ChatRepositoryImpl
    private lateinit var chatDao: ChatDao
    private lateinit var messageDao: MessageDao
    private lateinit var apiService: AIApiService
    private lateinit var securePreferences: SecurePreferences

    @Before
    fun setup() {
        chatDao = mockk(relaxed = true)
        messageDao = mockk(relaxed = true)
        apiService = mockk(relaxed = true)
        securePreferences = mockk(relaxed = true)

        repository = ChatRepositoryImpl(
            chatDao = chatDao,
            messageDao = messageDao,
            apiService = apiService,
            securePreferences = securePreferences
        )
    }

    @Test
    fun `getChats returns mapped domain models`() = runTest {
        val entities = listOf(
            ChatEntity(id = "1", title = "Chat 1", lastMessage = "Hi", timestamp = 100L, messageCount = 1)
        )
        coEvery { chatDao.getAllChats() } returns flowOf(entities)

        val chats = repository.getChats().first()

        assertEquals(1, chats.size)
        assertEquals("Chat 1", chats[0].title)
    }

    @Test
    fun `createChat inserts into database`() = runTest {
        val chat = repository.createChat("Test Chat")

        assertEquals("Test Chat", chat.title)
        coVerify { chatDao.insertChat(any()) }
    }

    @Test
    fun `deleteChat removes from database`() = runTest {
        repository.deleteChat("chat-1")

        coVerify { messageDao.deleteMessagesByChatId("chat-1") }
        coVerify { chatDao.deleteChat("chat-1") }
    }
}
