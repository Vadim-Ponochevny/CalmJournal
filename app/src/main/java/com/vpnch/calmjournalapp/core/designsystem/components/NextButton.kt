package com.vpnch.calmjournalapp.core.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vpnch.calmjournalapp.R
import com.vpnch.calmjournalapp.core.designsystem.components.OnboardingDimens.NEXT_BUTTON_HEIGHT
import com.vpnch.calmjournalapp.core.designsystem.components.OnboardingDimens.NEXT_BUTTON_WIDTH

object OnboardingDimens {
    const val NEXT_BUTTON_WIDTH = 290
    const val NEXT_BUTTON_HEIGHT = 49
    const val TOTAL_PAGES = 3
}

@Composable
fun NextButton(
    pagerState: PagerState,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentPage = pagerState.currentPage
    val isLastPage = currentPage >= OnboardingDimens.TOTAL_PAGES - 1

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onNext,
            modifier = modifier
                .width(NEXT_BUTTON_WIDTH.dp)
                .height(NEXT_BUTTON_HEIGHT.dp),
            enabled = true
        ) {
            Text(
                text = stringResource(
                    if (isLastPage) R.string.onboarding_start_journal
                    else R.string.onboarding_next
                ),
                color = MaterialTheme.colorScheme.background,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}