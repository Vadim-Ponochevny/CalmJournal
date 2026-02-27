package com.vpnch.calmjournalapp.domain.usecases.onboarding

import com.vpnch.calmjournalapp.domain.repository.LocalUserPreferences
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class ReadOnboardingCompletedUseCase @Inject constructor(
    private val userPreferences: LocalUserPreferences
) {
    operator fun invoke(): Flow<Boolean> = userPreferences.onboardingCompleted()
}