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
import androidx.compose.ui.res.stringResource
import com.vpnch.calmjournalapp.R
import com.vpnch.calmjournalapp.features.onboarding.OnboardingDimens.BOTTOM_SPACER
import com.vpnch.calmjournalapp.features.onboarding.OnboardingDimens.TOTAL_PAGES
import com.vpnch.calmjournalapp.features.onboarding.pages.AvatarSelectionPage
import com.vpnch.calmjournalapp.features.onboarding.pages.NameInputPage
import com.vpnch.calmjournalapp.features.onboarding.pages.WelcomeAnimationPage

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

    val nextButtonText = stringResource(
        if (pagerState.currentPage == TOTAL_PAGES - 1)
            R.string.onboarding_start_journal
        else
            R.string.onboarding_next
    )

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
                    0 -> WelcomeAnimationPage()
                    1 -> NameInputPage()
                    2 -> AvatarSelectionPage()
                }
            }

            Spacer(modifier = Modifier.height(BOTTOM_SPACER.dp))

            DotsIndicator(
                pagerState = pagerState,
                totalPages = TOTAL_PAGES
            )

            Spacer(modifier = Modifier.height(BOTTOM_SPACER.dp))

            NextButton(
                text = nextButtonText,
                onNext = navigateNext
            )

            Spacer(modifier = Modifier.height(BOTTOM_SPACER.dp))
        }
    }
}

