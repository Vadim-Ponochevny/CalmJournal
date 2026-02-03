package com.vpnch.calmjournalapp.core.domain.usecases.onboarding

data class OnboardingUseCases(
    val saveOnboardingData: SaveOnboardingData,
    val readOnboardingCompleted: ReadOnboardingCompleted
)