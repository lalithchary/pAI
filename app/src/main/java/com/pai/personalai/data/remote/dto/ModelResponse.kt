package com.pai.personalai.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ModelResponse(
    @SerializedName("data") val data: List<ModelDto>?,
    @SerializedName("error") val error: ErrorBody?
)

data class ModelDto(
    @SerializedName("id") val id: String,
    @SerializedName("owned_by") val ownedBy: String?,
    @SerializedName("created") val created: Long?
)
