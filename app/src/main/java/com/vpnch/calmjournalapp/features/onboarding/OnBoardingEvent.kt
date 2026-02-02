package com.vpnch.calmjournalapp.features.onboarding

import android.net.Uri

sealed class OnBoardingEvent {
    data class OnCustomAvatarSelected(val uri: Uri?): OnBoardingEvent()
    data class OnDefaultAvatarSelected(val resId: Int): OnBoardingEvent()
    data class SaveOnBoardingData(val name: String, val avatarState: AvatarState): OnBoardingEvent()
}