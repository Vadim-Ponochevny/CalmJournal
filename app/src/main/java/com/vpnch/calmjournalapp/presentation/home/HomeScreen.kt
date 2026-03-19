package com.vpnch.calmjournalapp.presentation.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.vpnch.calmjournalapp.presentation.designsystem.Dimens.contentHorizontalMaxWidth
import com.vpnch.calmjournalapp.presentation.navigation.Route.JournalScreen
import com.vpnch.calmjournalapp.presentation.home.components.GreetingHeader
import com.vpnch.calmjournalapp.presentation.home.components.StartRecordingButton
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state by viewModel.uiState.collectAsState()
    val user by viewModel.userData.collectAsState()

    val navBackStackEntry = navController.currentBackStackEntry
        ?: return

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.error) {
        state.error?.let {
            snackBarHostState.showSnackbar(it)
            viewModel.onEvent(HomeEvent.ErrorShown)
        }
    }

    LaunchedEffect(state.navigateToJournal) {
        if (state.navigateToJournal) {
            viewModel.observeJournalEntries()
        }
    }

    DisposableEffect(navBackStackEntry) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.onEvent(HomeEvent.NavigateBack)
            }
        }
        navBackStackEntry.lifecycle.addObserver(observer)

        onDispose {
            navBackStackEntry.lifecycle.removeObserver(observer)
        }
    }

    val quotes = listOf(
        "Твое состояние — самое важное",
        "Надо любить жизнь больше, чем смысл жизни",
        "Секрет успеха — в ежедневной рутине",
        "То, что вы записываете, перестает управлять вами"
    )

    var currentQuoteIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(4000)
            currentQuoteIndex = (currentQuoteIndex + 1) % quotes.size
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Column(
                    modifier = Modifier
                        .widthIn(max = contentHorizontalMaxWidth)
                        .fillMaxWidth()
                ) {
                    user?.let { GreetingHeader(user = it) }

                    AnimatedContent(
                        targetState = currentQuoteIndex,
                        transitionSpec = {
                            val enterTransition = slideInVertically(
                                initialOffsetY = { it / 4 },
                                animationSpec = tween(400)
                            ) + fadeIn(animationSpec = tween(400))

                            val exitTransition = slideOutVertically(
                                targetOffsetY = { -it / 4 },
                                animationSpec = tween(400)
                            ) + fadeOut(animationSpec = tween(400))

                            enterTransition togetherWith exitTransition
                        },
                        modifier = Modifier.padding(start = 28.dp, end = 28.dp, bottom = 28.dp)
                    ) { quoteIndex ->
                        Text(
                            text = quotes[quoteIndex],
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }

                    StartRecordingButton(
                        onClick = { viewModel.onEvent(HomeEvent.NavigateToJournal)
                            navController.navigate(JournalScreen.createRoute())  },
                        modifier = Modifier.padding(start = 28.dp, end = 28.dp, bottom = 24.dp)
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .widthIn(max = contentHorizontalMaxWidth)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Последние записи",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(start = 28.dp, end = 28.dp, bottom = 12.dp)
                    )
                }
            }
            state.journalEntries.forEach { record ->
                item {
                    Card(
                        modifier = Modifier
                            .widthIn(max = contentHorizontalMaxWidth)
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        shape = RoundedCornerShape(24.dp),
                        onClick = {
                            navController.navigate(JournalScreen.createRoute(record.id))
                            viewModel.onEvent(HomeEvent.NavigateToJournal)
                        },
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                    ) {
                        Text(
                            text = record.title,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

            }

            if (state.journalEntries.isEmpty() && !state.isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(64.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Записей пока нет.\nСоздайте первую!",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

    }
}


