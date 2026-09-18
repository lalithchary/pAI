package com.pai.personalai.domain.model

data class Attachment(
    val id: String,
    val type: AttachmentType,
    val uri: String,
    val name: String,
    val size: Long,
    val uploadStatus: AttachmentUploadStatus = AttachmentUploadStatus.PENDING
)

enum class AttachmentUploadStatus {
    PENDING,
    UPLOADING,
    UPLOADED,
    FAILED
}
