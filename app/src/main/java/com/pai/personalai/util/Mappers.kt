package com.pai.personalai.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pai.personalai.data.local.entity.ChatEntity
import com.pai.personalai.data.local.entity.MessageEntity
import com.pai.personalai.domain.model.AIModel
import com.pai.personalai.domain.model.Attachment
import com.pai.personalai.domain.model.Chat
import com.pai.personalai.domain.model.Message
import com.pai.personalai.domain.model.MessageRole
import com.pai.personalai.domain.model.MessageStatus
import com.pai.personalai.data.remote.dto.ModelDto

private val gson = Gson()

fun Message.toEntity(): MessageEntity {
    return MessageEntity(
        id = id,
        chatId = chatId,
        content = content,
        role = role.name,
        timestamp = timestamp,
        attachmentJson = gson.toJson(attachments),
        status = status.name
    )
}

fun MessageEntity.toDomain(): Message {
    val attachmentType = object : TypeToken<List<Attachment>>() {}.type
    val attachments: List<Attachment> = try {
        gson.fromJson(attachmentJson, attachmentType) ?: emptyList()
    } catch (e: Exception) {
        emptyList()
    }

    return Message(
        id = id,
        chatId = chatId,
        content = content,
        role = MessageRole.valueOf(role),
        timestamp = timestamp,
        attachments = attachments,
        status = MessageStatus.valueOf(status)
    )
}

fun Chat.toEntity(): ChatEntity {
    return ChatEntity(
        id = id,
        title = title,
        lastMessage = lastMessage,
        timestamp = timestamp,
        messageCount = messageCount
    )
}

fun ChatEntity.toDomain(): Chat {
    return Chat(
        id = id,
        title = title,
        lastMessage = lastMessage,
        timestamp = timestamp,
        messageCount = messageCount
    )
}

fun ModelDto.toDomain(): AIModel {
    return AIModel(
        id = id,
        name = id,
        ownedBy = ownedBy ?: ""
    )
}
