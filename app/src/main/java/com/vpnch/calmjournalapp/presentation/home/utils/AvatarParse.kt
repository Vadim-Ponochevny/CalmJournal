package com.vpnch.calmjournalapp.presentation.home.utils

sealed class AvatarModel {
    data class Custom(val uri: String) : AvatarModel()
    data class Default(val resId: Int) : AvatarModel()
}

fun parseAvatarData(avatarData: String?): AvatarModel? = when {
    avatarData == null -> null
    avatarData.startsWith("custom:") -> {
        val uriString = avatarData.removePrefix("custom:")
        AvatarModel.Custom(uriString)
    }
    avatarData.startsWith("default:") -> {
        val resIdString = avatarData.removePrefix("default:")
        resIdString.toIntOrNull()?.let { AvatarModel.Default(it) }
    }
    else -> null
}
