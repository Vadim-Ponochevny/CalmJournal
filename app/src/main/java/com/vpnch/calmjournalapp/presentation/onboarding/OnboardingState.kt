package com.vpnch.calmjournalapp.presentation.onboarding

data class OnboardingState(
    val currentPage: Int = 0,
    val name: String = "",
    val avatarState: AvatarState = AvatarState(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

