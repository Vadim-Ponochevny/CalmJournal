package com.vpnch.calmjournalapp.core.domain.usecases.onboarding

import com.vpnch.calmjournalapp.core.domain.manager.LocalUserPreferences
import jakarta.inject.Inject

class SaveOnboardingData @Inject constructor(
    private val userPreferences: LocalUserPreferences
) {
    suspend operator fun invoke(name: String, avatarData: String?) {
        userPreferences.saveOnboardingData(name, avatarData)
    }
}