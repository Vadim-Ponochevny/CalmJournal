package com.vpnch.calmjournalapp.core.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.vpnch.calmjournalapp.features.home.HomeScreen
import com.vpnch.calmjournalapp.features.onboarding.OnboardingScreen
import com.vpnch.calmjournalapp.features.onboarding.OnboardingViewModel

@Composable
fun NavGraph(startDestination: String) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ) {
            composable(Route.OnBoardingScreen.route) {
                val viewModel: OnboardingViewModel = hiltViewModel()

                val avatarState by viewModel.avatarState.collectAsStateWithLifecycle()

                OnboardingScreen(
                    avatarState = avatarState,
                    event = viewModel::onEvent,
                )
            }
        }

        navigation(
            route = Route.MainNavigation.route,
            startDestination = Route.MoodJournalScreen.route
        ) {
            composable(Route.MoodJournalScreen.route) {
                HomeScreen()
            }
        }
    }
}