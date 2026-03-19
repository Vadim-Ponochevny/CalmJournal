package com.vpnch.calmjournalapp.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vpnch.calmjournalapp.presentation.designsystem.Dimens.TOTAL_PAGES
import com.vpnch.calmjournalapp.domain.user.model.User
import com.vpnch.calmjournalapp.domain.user.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(OnboardingState())
    val state: StateFlow<OnboardingState> = _state

    fun onEvent(event: OnBoardingEvent) {
        when (event) {
            OnBoardingEvent.NavigateBack -> {
                _state.update { state ->
                    if (state.currentPage > 0) {
                        state.copy(currentPage = state.currentPage - 1)
                    } else state
                }
            }

            OnBoardingEvent.NavigateNext -> {
                _state.update { state ->
                    if (state.currentPage < TOTAL_PAGES - 1) {
                        state.copy(currentPage = state.currentPage + 1)
                    } else state
                }
            }

            is OnBoardingEvent.SubmitFinalData -> {
                saveOnboardingData(event.name,event.onSuccess)
            }

            is OnBoardingEvent.OnCustomAvatarSelected -> {
                _state.update { state ->
                    state.copy(
                        avatarState = state.avatarState.copy(
                            selectedType = AvatarType.CUSTOM,
                            uri = event.uri,
                            resId = null
                        )
                    )
                }
            }

            is OnBoardingEvent.OnDefaultAvatarSelected -> {
                _state.update { state ->
                    state.copy(
                        avatarState = state.avatarState.copy(
                            selectedType = AvatarType.DEFAULT_AVATAR,
                            resId = event.resId,
                            uri = null
                        )
                    )
                }
            }
        }
    }

    private fun saveOnboardingData(name: String, onSuccess: () -> Unit) {
        val avatarState = _state.value.avatarState

        val avatarData = when (avatarState.selectedType) {
            AvatarType.CUSTOM -> "custom:${avatarState.uri}"
            AvatarType.DEFAULT_AVATAR -> "default:${avatarState.resId}"
            else -> null
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                val newUser = User(
                    name = name,
                    avatarData = avatarData
                )

                userRepository.saveUserData(newUser)

                onSuccess()

            } catch (t: Throwable) {
                _state.update { it.copy(errorMessage = t.message) }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}
