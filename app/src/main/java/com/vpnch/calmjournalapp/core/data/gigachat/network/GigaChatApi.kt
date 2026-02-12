package com.vpnch.calmjournalapp.core.data.gigachat.network

import com.vpnch.calmjournalapp.BuildConfig
import com.vpnch.calmjournalapp.core.data.gigachat.network.dto.ChatCompletionRequest
import com.vpnch.calmjournalapp.core.data.gigachat.network.dto.ChatCompletionResponse
import com.vpnch.calmjournalapp.core.data.gigachat.network.dto.GigaTokenResponse
import retrofit2.http.*

interface GigaOAuthApi {

    @FormUrlEncoded
    @POST("api/v2/oauth")
    suspend fun getAccessToken(
        @Header("RqUID") rqUid: String,
        @Header("Authorization") authorizationKey: String = "Basic ${BuildConfig.GIGACHAT_AUTH_KEY}",
        @Field("scope") scope: String = "GIGACHAT_API_PERS"
    ): GigaTokenResponse
}

interface GigaChatApi {

//    @GET("api/v1/models")
//    suspend fun getModels(
//        @Header("Authorization") bearerToken: String
//    ): okhttp3.ResponseBody

    @POST("api/v1/chat/completions")
    suspend fun createChatCompletion(
        @Header("Authorization") bearerToken: String,
        @Body request: ChatCompletionRequest
    ): ChatCompletionResponse
}