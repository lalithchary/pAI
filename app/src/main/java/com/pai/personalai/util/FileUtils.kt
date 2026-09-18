package com.pai.personalai.util

import android.content.Context
import android.net.Uri
import android.util.Base64
import android.webkit.MimeTypeMap
import com.pai.personalai.domain.model.AttachmentType
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

object FileUtils {

    fun getFileFromUri(context: Context, uri: Uri): File? {
        val contentResolver = context.contentResolver
        val mimeType = contentResolver.getType(uri) ?: return null
        val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: "bin"

        val cacheDir = File(context.cacheDir, "attachments")
        if (!cacheDir.exists()) cacheDir.mkdirs()

        val cacheFile = File(cacheDir, "${UUID.randomUUID()}.$extension")

        return try {
            contentResolver.openInputStream(uri)?.use { input ->
                FileOutputStream(cacheFile).use { output ->
                    input.copyTo(output)
                }
            }
            cacheFile
        } catch (e: Exception) {
            null
        }
    }

    fun getFileSize(context: Context, uri: Uri): Long {
        return try {
            context.contentResolver.openFileDescriptor(uri, "r")?.statSize ?: 0L
        } catch (e: Exception) {
            0L
        }
    }

    fun validateFileSize(context: Context, uri: Uri): Boolean {
        return getFileSize(context, uri) <= Constants.MAX_FILE_SIZE_BYTES
    }

    fun getAttachmentType(mimeType: String?): AttachmentType {
        return when {
            mimeType?.startsWith("image/") == true -> AttachmentType.IMAGE
            mimeType == "application/pdf" -> AttachmentType.PDF
            mimeType?.contains("document") == true ||
            mimeType?.contains("msword") == true ||
            mimeType?.contains("wordprocessing") == true -> AttachmentType.DOC
            else -> AttachmentType.DOC
        }
    }

    fun getMimeType(context: Context, uri: Uri): String? {
        return context.contentResolver.getType(uri)
    }

    fun generateAttachmentId(): String {
        return "att_${UUID.randomUUID()}"
    }

    fun uriToBase64(context: Context, uri: Uri): String? {
        return try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val bytes = inputStream.readBytes()
                Base64.encodeToString(bytes, Base64.NO_WRAP)
            }
        } catch (e: Exception) {
            null
        }
    }

    fun cleanCache(context: Context) {
        val cacheDir = File(context.cacheDir, "attachments")
        if (cacheDir.exists()) {
            cacheDir.deleteRecursively()
        }
    }
}
