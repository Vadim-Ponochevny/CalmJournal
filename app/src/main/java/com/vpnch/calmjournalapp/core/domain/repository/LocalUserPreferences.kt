package com.vpnch.calmjournalapp.core.domain.repository

import com.vpnch.calmjournalapp.core.domain.models.User
import kotlinx.coroutines.flow.Flow

interface LocalUserPreferences {

    suspend fun saveOnboardingUserData(userData: User)

    fun onboardingCompleted(): Flow<Boolean>
}