package com.vpnch.calmjournalapp.core.domain.usecases

import com.vpnch.calmjournalapp.core.domain.localmanager.LocalUserPreferences
import kotlinx.coroutines.flow.Flow

class ReadOnboardingCompleted(
    private val userPreferences: LocalUserPreferences
) {
    operator fun invoke(): Flow<Boolean> = userPreferences.onboardingCompleted()
}