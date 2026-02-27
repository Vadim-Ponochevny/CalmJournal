package com.vpnch.calmjournalapp.domain.repository

import com.vpnch.calmjournalapp.domain.models.User
import kotlinx.coroutines.flow.Flow

interface LocalUserPreferences {

    suspend fun saveOnboardingUserData(userData: User)

    fun onboardingCompleted(): Flow<Boolean>

    fun userDataFlow(): Flow<User>
}