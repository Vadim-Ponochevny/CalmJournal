package com.vpnch.calmjournalapp.domain.insightentry.repository

import com.vpnch.calmjournalapp.data.insightentry.gigachat.network.dto.ChatMessage
import com.vpnch.calmjournalapp.domain.insightentry.result.JournalInsightResult

interface JournalInsightRepository {
    suspend fun analyzeJournalEntry(history: List<ChatMessage>): JournalInsightResult<String>
}