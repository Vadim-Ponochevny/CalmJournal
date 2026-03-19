package com.vpnch.calmjournalapp.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vpnch.calmjournalapp.presentation.entryeditor.JournalScreen
import com.vpnch.calmjournalapp.presentation.entryeditor.JournalViewModel
import com.vpnch.calmjournalapp.presentation.home.HomeScreen
import com.vpnch.calmjournalapp.presentation.onboarding.OnboardingScreen
import com.vpnch.calmjournalapp.presentation.onboarding.OnboardingViewModel

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
                val state by viewModel.state.collectAsStateWithLifecycle()
                OnboardingScreen(
                    state = state,
                    event = viewModel::onEvent,
                    navController = navController
                )
            }
        }

        navigation(
            route = Route.MainNavigation.route,
            startDestination = Route.HomeScreen.route
        ) {
            composable(Route.HomeScreen.route) {
                HomeScreen(navController = navController)
            }

            composable(
                route = Route.JournalScreen.routeWithArgs,
                arguments = listOf(
                    navArgument(Route.JournalScreen.argEntryId) {
                        type = NavType.LongType
                        defaultValue = -1L
                    }
                )
            ) {

                val viewModel: JournalViewModel = hiltViewModel()

                JournalScreen(
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
    }
}