package com.vpnch.calmjournalapp.data.journal.repository

import com.vpnch.calmjournalapp.data.journal.JournalDao
import com.vpnch.calmjournalapp.data.journal.model.toDomain
import com.vpnch.calmjournalapp.domain.journal.model.JournalEntry
import com.vpnch.calmjournalapp.domain.journal.model.toEntity
import com.vpnch.calmjournalapp.domain.journal.repository.JournalRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class JournalRepositoryImpl @Inject constructor(
    private val journalDao: JournalDao
) : JournalRepository {

    override fun getAllEntries(): Flow<List<JournalEntry>> {
        return journalDao.getAllEntries()
            .map { entities ->
                entities.map { it.toDomain() }
            }
    }

    override suspend fun upsertEntry(journalEntry: JournalEntry): Long {
        return journalDao.upsertJournal(journalEntry.toEntity())
    }

    override suspend fun getEntryById(id: Long): JournalEntry? {

        val dataJournalEntry = journalDao.getEntryById(id)

        return dataJournalEntry?.toDomain()
    }

    override suspend fun deleteEntry(id: Long) {
        journalDao.deleteEntry(id)
    }
}