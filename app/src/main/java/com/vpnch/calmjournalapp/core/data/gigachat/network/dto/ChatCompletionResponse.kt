package com.vpnch.calmjournalapp.core.data.gigachat.network.dto

data class ChatCompletionResponse(
    val id: String,
    val choices: List<ChatCompletionChoice>
)
