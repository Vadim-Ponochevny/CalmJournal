package com.vpnch.calmjournalapp.core.navigation

sealed class Route(
    val route: String
) {
    object AppStartNavigation : Route(route = "app_start")
    object OnBoardingScreen : Route(route = "onboarding")

    object MainNavigation : Route(route = "main")
    object MoodJournalScreen : Route(route = "mood_journal")

}