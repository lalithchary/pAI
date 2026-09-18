package com.pai.personalai.data.remote.api

import com.pai.personalai.data.remote.dto.ChatRequest
import com.pai.personalai.data.remote.dto.ChatResponse
import com.pai.personalai.data.remote.dto.ModelResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AIApiService {

    @GET("v1/models")
    suspend fun getModels(): ModelResponse

    @POST("v1/chat/completions")
    suspend fun chatCompletions(@Body request: ChatRequest): ChatResponse

    @Multipart
    @POST("v1/files")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part,
        @Part("purpose") purpose: RequestBody
    ): ResponseBody
}
