package com.vpnch.calmjournalapp.data.gigachat.network.dto

data class ChatCompletionResponse(
    val id: String,
    val choices: List<ChatCompletionChoice>
)
