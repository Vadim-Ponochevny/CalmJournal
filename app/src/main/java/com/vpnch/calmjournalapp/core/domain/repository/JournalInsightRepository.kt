package com.vpnch.calmjournalapp.core.domain.repository

import com.vpnch.calmjournalapp.core.domain.result.JournalInsightResult

interface JournalInsightRepository {
    suspend fun analyzeJournalEntry(text: String): JournalInsightResult<String>
}