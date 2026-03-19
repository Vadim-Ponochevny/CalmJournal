package com.vpnch.calmjournalapp.data.journal

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.vpnch.calmjournalapp.data.journal.model.JournalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {

    @Upsert
    suspend fun upsertJournal(journalEntity: JournalEntity) : Long

    @Query("SELECT * FROM journal_entries ORDER BY createdAt DESC")
    fun getAllEntries(): Flow<List<JournalEntity>>

    @Query("SELECT * FROM journal_entries WHERE id = :id")
    suspend fun getEntryById(id: Long): JournalEntity?

    @Query("DELETE FROM journal_entries WHERE id = :id")
    suspend fun deleteEntry(id: Long)
}