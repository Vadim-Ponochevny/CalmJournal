package com.vpnch.calmjournalapp.data.journalDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vpnch.calmjournalapp.data.journalDatabase.model.JournalEntity

@Database(
    entities = [JournalEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(JournalConverters::class)
abstract class JournalDatabase : RoomDatabase() {
    abstract fun journalDao(): JournalDao

    companion object {
        const val DATABASE_NAME = "journal_entries"
    }
}