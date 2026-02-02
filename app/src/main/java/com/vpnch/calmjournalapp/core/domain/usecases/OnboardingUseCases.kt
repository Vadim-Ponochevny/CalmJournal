package com.vpnch.calmjournalapp.core.domain.usecases

data class OnboardingUseCases(
    val saveOnboardingData: SaveOnboardingData,
    val readOnboardingCompleted: ReadOnboardingCompleted
)