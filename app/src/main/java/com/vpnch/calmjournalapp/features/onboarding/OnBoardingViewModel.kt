package com.vpnch.calmjournalapp.features.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vpnch.calmjournalapp.core.data.datastore.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    val userName = userPreferences.userNameFlow

    private val _nameError = MutableStateFlow<String?>(null)
    val nameError = _nameError.asStateFlow()

    fun onNameChange(newName: String) {
        _nameError.value = if (newName.trim().isEmpty()) "Введите имя" else null
    }

    fun saveAndValidateName(): Boolean {
        val trimmed = userName.value.trim()
        return if (trimmed.isNotEmpty()) {
            viewModelScope.launch {
                userPreferences.saveName(trimmed)
            }
            true
        } else false
    }
}