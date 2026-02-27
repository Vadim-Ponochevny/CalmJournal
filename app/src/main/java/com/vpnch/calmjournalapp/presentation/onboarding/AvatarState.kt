package com.vpnch.calmjournalapp.presentation.onboarding

import android.net.Uri
import androidx.compose.runtime.Immutable

// custom - uri from gallery or camera
// default - one of default avatars in resource
enum class AvatarType {
    CUSTOM, DEFAULT_AVATAR, NONE
}

@Immutable
data class AvatarState(
    val selectedType: AvatarType = AvatarType.NONE,
    val uri: Uri? = null,
    val resId: Int? = null,
)