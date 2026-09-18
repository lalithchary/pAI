package com.pai.personalai.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ChatResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("choices") val choices: List<Choice>?,
    @SerializedName("usage") val usage: Usage?,
    @SerializedName("error") val error: ErrorBody?
)

data class Choice(
    @SerializedName("index") val index: Int,
    @SerializedName("message") val message: MessageDto?,
    @SerializedName("finish_reason") val finishReason: String?
)

data class Usage(
    @SerializedName("prompt_tokens") val promptTokens: Int,
    @SerializedName("completion_tokens") val completionTokens: Int,
    @SerializedName("total_tokens") val totalTokens: Int
)

data class ErrorBody(
    @SerializedName("message") val message: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("code") val code: String?
)
