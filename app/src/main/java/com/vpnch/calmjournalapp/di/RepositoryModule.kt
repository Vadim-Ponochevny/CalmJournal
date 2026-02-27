package com.vpnch.calmjournalapp.di

import com.vpnch.calmjournalapp.data.gigachat.local.GigaChatPreferences
import com.vpnch.calmjournalapp.data.gigachat.network.GigaChatApi
import com.vpnch.calmjournalapp.data.gigachat.network.GigaOAuthApi
import com.vpnch.calmjournalapp.data.gigachat.repository.JournalInsightRepositoryImpl
import com.vpnch.calmjournalapp.data.journalDatabase.JournalDao
import com.vpnch.calmjournalapp.data.journalDatabase.JournalDatabase
import com.vpnch.calmjournalapp.data.journalDatabase.repository.JournalRepositoryImpl
import com.vpnch.calmjournalapp.domain.repository.JournalInsightRepository
import com.vpnch.calmjournalapp.domain.repository.JournalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideJournalDao(
        database: JournalDatabase
    ): JournalDao = database.journalDao()

    @Provides
    fun provideJournalRepository(
        journalDao: JournalDao
    ): JournalRepository {
        return JournalRepositoryImpl(journalDao)
    }

    @Provides
    fun provideJournalInsightRepository(
        oauthApi: GigaOAuthApi,
        chatApi: GigaChatApi,
        gigaPrefs: GigaChatPreferences
    ): JournalInsightRepository {
        return JournalInsightRepositoryImpl(oauthApi, chatApi, gigaPrefs)
    }
}