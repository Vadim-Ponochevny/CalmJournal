package com.vpnch.calmjournalapp.domain.usecases.onboarding

import com.vpnch.calmjournalapp.domain.models.User
import com.vpnch.calmjournalapp.domain.repository.LocalUserPreferences
import jakarta.inject.Inject

class SaveOnboardingDataUseCase @Inject constructor(
    private val userPreferences: LocalUserPreferences
) {
    suspend operator fun invoke(user: User) {
        userPreferences.saveOnboardingUserData(user)
    }
}