package com.pai.personalai.presentation.screens.chat

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pai.personalai.domain.model.Chat
import com.pai.personalai.domain.model.Message
import com.pai.personalai.domain.repository.ChatRepository
import com.pai.personalai.domain.repository.ConfigRepository
import com.pai.personalai.domain.usecase.CreateChatUseCase
import com.pai.personalai.domain.usecase.DeleteChatUseCase
import com.pai.personalai.domain.usecase.SendMessageUseCase
import com.pai.personalai.util.FileUtils
import com.pai.personalai.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import android.util.Log
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val configRepository: ConfigRepository,
    private val sendMessageUseCase: SendMessageUseCase,
    private val createChatUseCase: CreateChatUseCase,
    private val deleteChatUseCase: DeleteChatUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private var messagesJob: Job? = null

    init {
        loadChats()
        loadSelectedModel()
    }

    private fun loadChats() {
        chatRepository.getChats().onEach { chats ->
            _uiState.update { it.copy(chats = chats) }
        }.launchIn(viewModelScope)
    }

    private fun loadSelectedModel() {
        val config = configRepository.getConfig()
        _uiState.update { it.copy(selectedModel = config?.selectedModel ?: "") }
    }

    fun selectChat(chatId: String) {
        Log.d("ChatViewModel", "selectChat: $chatId")
        _uiState.update { it.copy(currentChatId = chatId, messages = emptyList()) }
        messagesJob?.cancel()
        messagesJob = chatRepository.getMessages(chatId).onEach { messages ->
            Log.d("ChatViewModel", "Messages updated for $chatId: ${messages.size}")
            _uiState.update { it.copy(messages = messages) }
        }.launchIn(viewModelScope)
    }

    fun startNewChat() {
        viewModelScope.launch {
            val chat = createChatUseCase("New Chat")
            selectChat(chat.id)
        }
    }

    fun onInputChange(text: String) {
        _uiState.update { it.copy(inputText = text) }
    }

    fun addAttachedFile(uri: Uri) {
        val name = getFileName(uri)
        val type = context.contentResolver.getType(uri) ?: ""
        val size = FileUtils.getFileSize(context, uri)
        val sizeStr = formatFileSize(size)

        val newAttachment = Attachment(
            uri = uri,
            name = name,
            type = type,
            size = sizeStr,
            isUploading = false // Can simulate upload here if needed
        )
        _uiState.update { it.copy(attachedFiles = it.attachedFiles + newAttachment) }
    }

    fun removeAttachedFile(attachment: Attachment) {
        _uiState.update { it.copy(attachedFiles = it.attachedFiles - attachment) }
    }

    fun clearAttachedFiles() {
        _uiState.update { it.copy(attachedFiles = emptyList()) }
    }

    private fun getFileName(uri: Uri): String {
        var name = ""
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (cursor.moveToFirst()) {
                name = cursor.getString(nameIndex)
            }
        }
        return name.ifBlank { uri.lastPathSegment ?: "unknown" }
    }

    private fun formatFileSize(size: Long): String {
        if (size <= 0) return "0 B"
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()
        return String.format(Locale.getDefault(), "%.1f %s", size / Math.pow(1024.0, digitGroups.toDouble()), units[digitGroups])
    }

    fun sendMessage() {
        val state = _uiState.value
        val content = state.inputText.trim()
        val currentChatId = state.currentChatId

        if (content.isBlank() && state.attachedFiles.isEmpty()) return

        _uiState.update {
            it.copy(
                inputText = "",
                isSending = true,
                isTyping = true,
                error = null
            )
        }

        viewModelScope.launch {
            val chatId = if (currentChatId == null) {
                Log.d("ChatViewModel", "Creating new chat for message")
                val newChat = createChatUseCase(content.take(20).ifBlank { "New Chat" })
                selectChat(newChat.id)
                newChat.id
            } else {
                currentChatId
            }

            val originalAttachments = state.attachedFiles
            val attachmentUris = originalAttachments.map { it.uri }
            _uiState.update { it.copy(attachedFiles = emptyList()) }

            Log.d("ChatViewModel", "Calling sendMessageUseCase for $chatId")
            when (val result = sendMessageUseCase(chatId, content, state.selectedModel, attachmentUris)) {
                is Resource.Success -> {
                    _uiState.update { it.copy(isSending = false, isTyping = false) }
                }
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isSending = false,
                            isTyping = false,
                            error = result.message,
                            inputText = content, // Restore text on error
                            attachedFiles = originalAttachments // Restore attachments on error
                        )
                    }
                }
                is Resource.Loading -> {}
            }
        }
    }

    fun deleteChat(chatId: String) {
        viewModelScope.launch {
            deleteChatUseCase(chatId)
            if (_uiState.value.currentChatId == chatId) {
                _uiState.update { it.copy(currentChatId = null, messages = emptyList()) }
            }
        }
    }

    fun toggleAttachmentSheet() {
        _uiState.update { it.copy(showAttachmentSheet = !it.showAttachmentSheet) }
    }

    fun hideAttachmentSheet() {
        _uiState.update { it.copy(showAttachmentSheet = false) }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
