package com.vpnch.calmjournalapp.features.onboarding

import android.net.Uri

// custom - uri from gallery or camera
// default - one of default avatars in resource
enum class AvatarType {
    CUSTOM, DEFAULT_AVATAR, NONE
}

data class AvatarState(
    val selectedType: AvatarType = AvatarType.NONE,
    val uri: Uri? = null,
    val resId: Int? = null,
)