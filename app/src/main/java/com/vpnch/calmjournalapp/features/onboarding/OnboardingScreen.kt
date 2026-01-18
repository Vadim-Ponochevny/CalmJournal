package com.vpnch.calmjournalapp.features.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vpnch.calmjournalapp.core.designsystem.components.DotsIndicator
import com.vpnch.calmjournalapp.core.designsystem.components.BackButton
import com.vpnch.calmjournalapp.core.designsystem.components.NextButton
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import com.vpnch.calmjournalapp.features.onboarding.OnboardingDimens.BOTTOM_SPACER
import com.vpnch.calmjournalapp.features.onboarding.OnboardingDimens.TOTAL_PAGES

object OnboardingDimens {
    const val BOTTOM_SPACER = 25
    const val TOTAL_PAGES = 3
}

@Composable
fun OnboardingScreen(
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { TOTAL_PAGES })
    val coroutineScope = rememberCoroutineScope()

    val navigateNext: () -> Unit = {
        if (pagerState.currentPage < TOTAL_PAGES - 1) {
            coroutineScope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
        } else {
            onComplete()
        }
    }

    val navigateBack: () -> Unit = {
        coroutineScope.launch {
            pagerState.animateScrollToPage(pagerState.currentPage - 1)
        }
    }

    val showBackButton = pagerState.currentPage > 0

    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            if (showBackButton) {
                BackButton(onClick = navigateBack)
            }

            HorizontalPager(
                state = pagerState,
                userScrollEnabled = true,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { page ->
                when (page) {
                    0 -> WelcomeAnimationScreen()
                    1 -> NameInputScreen()
                    2 -> AvatarSelectionScreen()
                }
            }

            Spacer(modifier = Modifier.height(BOTTOM_SPACER.dp))

            DotsIndicator(
                pagerState = pagerState,
                totalPages = TOTAL_PAGES
            )

            Spacer(modifier = Modifier.height(BOTTOM_SPACER.dp))

            NextButton(
                pagerState = pagerState,
                onNext = navigateNext
            )

            Spacer(modifier = Modifier.height(BOTTOM_SPACER.dp))
        }
    }
}

