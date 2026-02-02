package com.vpnch.calmjournalapp.core.domain.usecases

import com.vpnch.calmjournalapp.core.domain.localmanager.LocalUserPreferences

class SaveOnboardingData(
    private val userPreferences: LocalUserPreferences
) {
    suspend operator fun invoke(name: String, avatarData: String?) {
        userPreferences.saveOnboardingData(name, avatarData)
    }
}