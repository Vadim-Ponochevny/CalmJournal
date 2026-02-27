package com.vpnch.calmjournalapp.domain.repository

import com.vpnch.calmjournalapp.data.gigachat.network.dto.ChatMessage
import com.vpnch.calmjournalapp.domain.result.JournalInsightResult

interface JournalInsightRepository {
    suspend fun analyzeJournalEntry(history: List<ChatMessage>): JournalInsightResult<String>
}