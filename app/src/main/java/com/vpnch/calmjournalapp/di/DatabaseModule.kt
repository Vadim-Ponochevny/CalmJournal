package com.vpnch.calmjournalapp.di

import android.content.Context
import androidx.room.Room
import com.vpnch.calmjournalapp.data.journalDatabase.JournalDatabase
import com.vpnch.calmjournalapp.data.journalDatabase.MIGRATION_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideJournalDatabase(
        @ApplicationContext context: Context
    ): JournalDatabase {
        return Room.databaseBuilder(
                context,
                JournalDatabase::class.java,
                JournalDatabase.Companion.DATABASE_NAME
            ).addMigrations(MIGRATION_1_2).build()
    }
}