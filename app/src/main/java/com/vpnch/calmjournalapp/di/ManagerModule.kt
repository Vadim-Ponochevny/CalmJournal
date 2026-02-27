package com.vpnch.calmjournalapp.di

import android.app.Application
import com.vpnch.calmjournalapp.data.gigachat.local.GigaChatPreferences
import com.vpnch.calmjournalapp.data.user.local.LocalUserPreferencesImpl
import com.vpnch.calmjournalapp.domain.repository.LocalUserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ManagerModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ): LocalUserPreferences = LocalUserPreferencesImpl(application)

    @Provides
    @Singleton
    fun provideGigaChatPreferences(
        application: Application
    ): GigaChatPreferences = GigaChatPreferences(application)
}
