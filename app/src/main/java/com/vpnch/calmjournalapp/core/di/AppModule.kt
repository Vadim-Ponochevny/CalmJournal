package com.vpnch.calmjournalapp.core.di

import android.app.Application
import com.vpnch.calmjournalapp.core.data.localmanager.LocalUserPreferencesImpl
import com.vpnch.calmjournalapp.core.domain.localmanager.LocalUserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ): LocalUserPreferences = LocalUserPreferencesImpl(application)
}
