package com.vpnch.calmjournalapp.di

import com.vpnch.calmjournalapp.data.insightentry.gigachat.local.GigaChatPreferences
import com.vpnch.calmjournalapp.data.insightentry.gigachat.network.GigaChatApi
import com.vpnch.calmjournalapp.data.insightentry.gigachat.network.GigaOAuthApi
import com.vpnch.calmjournalapp.data.insightentry.gigachat.repository.JournalInsightRepositoryImpl
import com.vpnch.calmjournalapp.data.journal.JournalDao
import com.vpnch.calmjournalapp.data.journal.JournalDatabase
import com.vpnch.calmjournalapp.data.journal.repository.JournalRepositoryImpl
import com.vpnch.calmjournalapp.domain.insightentry.repository.JournalInsightRepository
import com.vpnch.calmjournalapp.domain.journal.repository.JournalRepository
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