package com.vpnch.calmjournalapp.data.user.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.vpnch.calmjournalapp.domain.user.model.User
import com.vpnch.calmjournalapp.domain.user.repository.UserRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

@Singleton
class UserRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : UserRepository {
    companion object {
        private const val PREFS_FILE_NAME = "user_prefs"
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = PREFS_FILE_NAME
    )

    private val USER_NAME_KEY = stringPreferencesKey("user_name")
    private val USER_AVATAR_DATA_KEY = stringPreferencesKey("avatar_data")

    override suspend fun saveUserData(user: User) {
        context.dataStore.edit { prefs ->
            prefs[USER_NAME_KEY] = user.name
            user.avatarData?.let { prefs[USER_AVATAR_DATA_KEY] = it }
        }
    }

    override suspend fun getUserData(): User? {
        val prefs = context.dataStore.data.first()
        val name = prefs[USER_NAME_KEY] ?: return null // first time opening
        return User(name = name, avatarData = prefs[USER_AVATAR_DATA_KEY])
    }

    override fun userDataFlow(): Flow<User> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { prefs ->
                User(
                    name = prefs[USER_NAME_KEY] ?: "",
                    avatarData = prefs[USER_AVATAR_DATA_KEY],
                )
            }
    }

}