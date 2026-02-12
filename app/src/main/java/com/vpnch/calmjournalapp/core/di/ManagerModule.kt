package com.vpnch.calmjournalapp.core.di

import android.app.Application
import com.vpnch.calmjournalapp.core.data.user.local.LocalUserPreferencesImpl
import com.vpnch.calmjournalapp.core.domain.repository.LocalUserPreferences
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

}
