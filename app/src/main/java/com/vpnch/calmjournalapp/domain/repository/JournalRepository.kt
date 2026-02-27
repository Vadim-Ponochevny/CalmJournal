package com.vpnch.calmjournalapp.domain.repository

import com.vpnch.calmjournalapp.domain.models.JournalEntry
import kotlinx.coroutines.flow.Flow

interface JournalRepository {

    fun getAllEntries(): Flow<List<JournalEntry>>

    suspend fun upsertEntry(entry: JournalEntry) : Long

    suspend fun getEntryById(id: Long): JournalEntry?

    suspend fun deleteEntry(id: Long)

}