package com.vpnch.calmjournalapp.core.domain.usecases.onboarding

import com.vpnch.calmjournalapp.core.domain.models.User
import com.vpnch.calmjournalapp.core.domain.repository.LocalUserPreferences
import jakarta.inject.Inject

class SaveOnboardingDataUseCase @Inject constructor(
    private val userPreferences: LocalUserPreferences
) {
    suspend operator fun invoke(user: User) {
        userPreferences.saveOnboardingUserData(user)
    }
}