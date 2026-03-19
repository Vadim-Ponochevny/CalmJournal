package com.vpnch.calmjournalapp.di

import android.app.Application
import com.vpnch.calmjournalapp.data.insightentry.gigachat.local.GigaChatPreferences
import com.vpnch.calmjournalapp.data.user.local.UserRepositoryImpl
import com.vpnch.calmjournalapp.domain.user.repository.UserRepository
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
    ): UserRepository = UserRepositoryImpl(application)

    @Provides
    @Singleton
    fun provideGigaChatPreferences(
        application: Application
    ): GigaChatPreferences = GigaChatPreferences(application)
}
