package com.vpnch.calmjournalapp.presentation.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vpnch.calmjournalapp.presentation.onboarding.components.DotsIndicator
import com.vpnch.calmjournalapp.presentation.designsystem.components.BackButton
import com.vpnch.calmjournalapp.presentation.onboarding.components.NextButton
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import com.vpnch.calmjournalapp.R
import com.vpnch.calmjournalapp.presentation.designsystem.Dimens.TOTAL_PAGES
import com.vpnch.calmjournalapp.presentation.designsystem.Dimens.fieldMaxWidth
import com.vpnch.calmjournalapp.presentation.onboarding.pages.AvatarSelectionPage
import com.vpnch.calmjournalapp.presentation.onboarding.pages.NameInputPage
import com.vpnch.calmjournalapp.presentation.onboarding.pages.WelcomeAnimationPage
import com.vpnch.calmjournalapp.presentation.designsystem.Dimens.spacingLarge
import com.vpnch.calmjournalapp.presentation.designsystem.Dimens.spacingMedium
import com.vpnch.calmjournalapp.presentation.designsystem.Dimens.spacingTiny

@Composable
fun OnboardingScreen(
    event: (OnBoardingEvent) -> Unit,
    modifier: Modifier = Modifier,
    state: OnboardingState,
) {
    val nameState = rememberTextFieldState(state.name)

    val pagerState = rememberPagerState(
        pageCount = { TOTAL_PAGES }
    )

    LaunchedEffect(state.currentPage) {
        pagerState.animateScrollToPage(state.currentPage)
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(pagerState.currentPage) {
        keyboardController?.hide()
        focusManager.clearFocus()
    }

    val isCurrentPageValid = when (pagerState.currentPage) {
        0 -> true
        1 -> nameState.text.isNotBlank()
        2 -> state.avatarState.selectedType != AvatarType.NONE
        else -> true
    }

    val navigateNext = {
        if (isCurrentPageValid) {
            val isLastPage = state.currentPage == TOTAL_PAGES - 1
            if (isLastPage) {
                event(OnBoardingEvent.SubmitFinalData(nameState.text.toString()))
            } else {
                event(OnBoardingEvent.NavigateNext)
            }
        }
    }

    val nextButtonText = stringResource(
        if (state.currentPage == TOTAL_PAGES - 1)
            R.string.onboarding_start_journal
        else
            R.string.onboarding_next
    )

    val showBackButton = state.currentPage > 0

    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxSize()) {

                if (showBackButton) {
                    BackButton(onClick = { event(OnBoardingEvent.NavigateBack) })
                }

                HorizontalPager(
                    state = pagerState,
                    userScrollEnabled = false,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) { page ->
                    when (page) {
                        0 -> WelcomeAnimationPage()
                        1 -> NameInputPage(
                            nameState = nameState,
                            navigateNext = navigateNext
                        )

                        2 -> AvatarSelectionPage(
                            avatarState = state.avatarState,
                            onCustomAvatarSelected = { uri ->
                                event(OnBoardingEvent.OnCustomAvatarSelected(uri = uri))
                            },
                            onDefaultAvatarSelected = { resId ->
                                event(OnBoardingEvent.OnDefaultAvatarSelected(resId = resId))
                            },
                        )
                    }
                }

                Spacer(modifier = Modifier.height(spacingLarge))

                DotsIndicator(
                    pagerState = pagerState,
                    totalPages = TOTAL_PAGES
                )

                Spacer(modifier = Modifier.height(spacingLarge))

                NextButton(
                    text = nextButtonText,
                    onNext = navigateNext,
                    enabled = isCurrentPageValid && !state.isLoading
                )

                if (!state.isLoading && state.errorMessage != null) {
                    Text(
                        text = state.errorMessage ?: "",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .width(fieldMaxWidth)
                            .padding(
                                horizontal = spacingMedium,
                                vertical = spacingTiny
                            ),
                        style = MaterialTheme.typography.labelLarge,
                    )
                }

                Spacer(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .height(spacingLarge)
                )
            }
        }
    }
}


