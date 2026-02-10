package com.vpnch.calmjournalapp.core.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.vpnch.calmjournalapp.core.domain.manager.LocalUserPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class LocalUserPreferencesImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LocalUserPreferences {
    companion object {
        private const val PREFS_FILE_NAME = "user_prefs"
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = PREFS_FILE_NAME
    )

    private val USER_NAME_KEY = stringPreferencesKey("user_name")
    private val USER_AVATAR_DATA_KEY = stringPreferencesKey("avatar_data")
    private val ONBOARDING_COMPLETED_KEY = booleanPreferencesKey("onboarding_completed")

    override fun onboardingCompleted(): Flow<Boolean>{
        return context.dataStore.data
            .map { prefs -> (prefs[ONBOARDING_COMPLETED_KEY] ?: false) }
    }

    override suspend fun saveOnboardingData(name: String, avatarData: String?) {
        context.dataStore.edit { prefs ->
            prefs[USER_NAME_KEY] = name
            avatarData?.let { prefs[USER_AVATAR_DATA_KEY] = it }
            prefs[ONBOARDING_COMPLETED_KEY] = true
        }
    }

}