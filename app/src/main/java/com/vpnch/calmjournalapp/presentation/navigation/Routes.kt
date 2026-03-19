package com.vpnch.calmjournalapp.presentation.navigation

sealed class Route(val route: String) {
    object AppStartNavigation : Route("app_start")
    object MainNavigation : Route("main")

    object OnBoardingScreen : Route("onboarding")
    object HomeScreen : Route("home_screen")

    object JournalScreen : Route("journal_screen") {
        const val argEntryId = "entryId"

        val routeWithArgs = "$route?$argEntryId={$argEntryId}"

        fun createRoute(id: Long? = null): String {
            return if (id != null && id != -1L) "$route?$argEntryId=$id" else route
        }
    }
}
