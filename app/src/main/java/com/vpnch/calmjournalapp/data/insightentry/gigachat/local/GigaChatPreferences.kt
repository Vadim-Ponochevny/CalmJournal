package com.vpnch.calmjournalapp.data.insightentry.gigachat.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class GigaChatPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private const val PREFS_FILE_NAME = "giga_chat_prefs"
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = PREFS_FILE_NAME
    )

    private val GIGA_TOKEN_KEY = stringPreferencesKey("access_token")
    private val GIGA_EXPIRES_AT_KEY = longPreferencesKey("expires_at")

    fun gigaToken(): Flow<GigaTokenEntity?> {
        return context.dataStore.data.map { prefs ->
            val token = prefs[GIGA_TOKEN_KEY]
            val expires = prefs[GIGA_EXPIRES_AT_KEY] ?: 0L
            if (token != null && expires != 0L) {
                GigaTokenEntity(token, expires)
            } else null
        }
    }

    suspend fun saveGigaToken(token: String, expiresAt: Long) {
        context.dataStore.edit { prefs ->
            prefs[GIGA_TOKEN_KEY] = token
            prefs[GIGA_EXPIRES_AT_KEY] = expiresAt
        }
    }

    suspend fun clearGigaToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(GIGA_TOKEN_KEY)
            prefs.remove(GIGA_EXPIRES_AT_KEY)
        }
    }
}