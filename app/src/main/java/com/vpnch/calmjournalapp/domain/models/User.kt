package com.vpnch.calmjournalapp.domain.models

// ** AVATAR DATA **
// AvatarType.CUSTOM -> "custom:${avatarState.uri}"
// AvatarType.DEFAULT_AVATAR -> "default:${avatarState.resId}"

data class User(
    val name: String,
    val avatarData: String?
)
