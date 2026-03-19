package com.vpnch.calmjournalapp.presentation.onboarding

import android.net.Uri

sealed class OnBoardingEvent {
    object NavigateBack: OnBoardingEvent()
    object NavigateNext : OnBoardingEvent()
    data class SubmitFinalData(
        val name: String,
        val onSuccess: () -> Unit
    ) : OnBoardingEvent()

    data class OnCustomAvatarSelected(val uri: Uri?): OnBoardingEvent()
    data class OnDefaultAvatarSelected(val resId: Int): OnBoardingEvent()
}