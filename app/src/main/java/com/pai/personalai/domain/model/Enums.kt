package com.pai.personalai.domain.model

enum class MessageRole {
    USER,
    ASSISTANT,
    SYSTEM
}

enum class MessageStatus {
    SENDING,
    SENT,
    FAILED
}

enum class AttachmentType {
    IMAGE,
    PDF,
    DOC
}

enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM
}
