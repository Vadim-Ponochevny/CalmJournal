package com.vpnch.calmjournalapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vpnch.calmjournalapp.domain.usecases.onboarding.ReadOnboardingCompletedUseCase
import com.vpnch.calmjournalapp.presentation.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val readOnboardingCompleted: ReadOnboardingCompletedUseCase
) : ViewModel() {

    var splashState by mutableStateOf(true)
        private set

    var startDestination by mutableStateOf(Route.AppStartNavigation.route)
        private set

    init {
        viewModelScope.launch {
            readOnboardingCompleted().collect { isOnboardingCompleted ->
                startDestination = if (isOnboardingCompleted) {
                    Route.MainNavigation.route
                } else {
                    Route.AppStartNavigation.route
                }
                delay(300)
                splashState = false
            }
        }
    }
}