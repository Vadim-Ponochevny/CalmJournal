package com.vpnch.calmjournalapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.vpnch.calmjournalapp.presentation.designsystem.theme.CalmJournalTheme
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.vpnch.calmjournalapp.presentation.navigation.NavGraph
import com.vpnch.calmjournalapp.presentation.navigation.Route
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isUserDataLoaded == null
            }
        }

        setContent {
            CalmJournalTheme {
                val isLoaded = viewModel.isUserDataLoaded

                if (isLoaded != null) {

                    val startRoute = remember {
                        if (isLoaded) Route.MainNavigation.route
                        else Route.AppStartNavigation.route
                    }

                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .statusBarsPadding()
                            .windowInsetsPadding(WindowInsets.navigationBars),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavGraph(startDestination = startRoute)
                    }
                }
            }
        }
    }
}