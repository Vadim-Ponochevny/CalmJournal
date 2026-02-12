package com.vpnch.calmjournalapp.core.data.gigachat.network.dto

data class ChatCompletionRequest(
    val model: String = "GigaChat",
    val messages: List<ChatMessage>,
    val temperature: Double = 0.4,
    val max_tokens: Int = 512
)
