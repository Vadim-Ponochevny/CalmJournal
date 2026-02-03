package com.vpnch.calmjournalapp.features.onboarding

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vpnch.calmjournalapp.core.domain.usecases.onboarding.SaveOnboardingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val saveOnboardingData: SaveOnboardingData
) : ViewModel() {

    private val _avatarState = MutableStateFlow(AvatarState())
    val avatarState: StateFlow<AvatarState> = _avatarState.asStateFlow()

    fun onEvent(event: OnBoardingEvent) {
        when(event){
            is OnBoardingEvent.OnCustomAvatarSelected -> {
                onCustomAvatarSelected(event.uri)
            }
            is OnBoardingEvent.OnDefaultAvatarSelected -> {
                onDefaultAvatarSelected(event.resId)
            }
            is OnBoardingEvent.SaveOnBoardingData -> {
                saveOnboardingData(
                    name = event.name,
                    avatarState = event.avatarState
                )
            }
        }
    }

    private fun onCustomAvatarSelected(uri: Uri?) {
        _avatarState.value = _avatarState.value.copy(
            selectedType = AvatarType.CUSTOM,
            uri = uri
        )
    }

    private fun onDefaultAvatarSelected(resId: Int) {
        _avatarState.value = _avatarState.value.copy(
            selectedType = AvatarType.DEFAULT_AVATAR,
            resId = resId
        )
    }
    private fun saveOnboardingData(name: String, avatarState: AvatarState)  {

        val avatarData = when (avatarState.selectedType) {
            AvatarType.CUSTOM -> "custom:${avatarState.uri}"
            AvatarType.DEFAULT_AVATAR -> "default:${avatarState.resId}"
            else -> null
        }

        viewModelScope.launch {
            saveOnboardingData(
                name = name,
                avatarData = avatarData
            )
        }
    }

}