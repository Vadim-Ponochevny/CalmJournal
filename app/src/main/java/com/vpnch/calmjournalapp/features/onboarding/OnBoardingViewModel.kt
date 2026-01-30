package com.vpnch.calmjournalapp.features.onboarding

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vpnch.calmjournalapp.core.data.datastore.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _selectedUri = MutableStateFlow<Uri?>(null)
    val selectedUri: StateFlow<Uri?> = _selectedUri.asStateFlow()

    private val _selectedDefaultAvatar = MutableStateFlow<Int?>(null)
    val selectedDefaultAvatar: StateFlow<Int?> = _selectedDefaultAvatar.asStateFlow()

    fun onCustomAvatarSelected(uri: Uri?) {
        _selectedUri.value = uri
        _selectedDefaultAvatar.value = null
    }

    fun onDefaultAvatarSelected(resId: Int) {
        _selectedDefaultAvatar.value = resId
        _selectedUri.value = null
    }

    fun saveName(name: String) {
        viewModelScope.launch {
            userPreferences.saveName(name)
        }
    }

}