package com.pai.personalai.domain.model

data class Chat(
    val id: String,
    val title: String = "New Chat",
    val lastMessage: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val messageCount: Int = 0
)
