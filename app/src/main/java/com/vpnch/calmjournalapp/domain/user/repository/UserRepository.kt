package com.vpnch.calmjournalapp.domain.user.repository

import com.vpnch.calmjournalapp.domain.user.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun saveUserData(userData: User)

    suspend fun getUserData(): User?

    fun userDataFlow(): Flow<User>
}