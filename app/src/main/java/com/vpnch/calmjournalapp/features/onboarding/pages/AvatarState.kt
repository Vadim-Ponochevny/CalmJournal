package com.vpnch.calmjournalapp.features.onboarding.pages

import android.net.Uri

// custom - uri from gallery or camera
// default - one of default avatars in resource
enum class AvatarType {
    CUSTOM, DEFAULT, NONE
}

data class AvatarState(
    val selectedType: AvatarType = AvatarType.NONE,
    val uri: Uri,
    val resId: Int
)