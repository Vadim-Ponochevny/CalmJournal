package com.vpnch.calmjournalapp.core.domain.manager

import kotlinx.coroutines.flow.Flow

interface LocalUserPreferences {

    suspend fun saveOnboardingData(name: String, avatarData: String?)

    fun onboardingCompleted(): Flow<Boolean>
}