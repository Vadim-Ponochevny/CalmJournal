package com.vpnch.calmjournalapp.features.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.vpnch.calmjournalapp.core.designsystem.Dimens.dotSize
import com.vpnch.calmjournalapp.core.designsystem.Dimens.spacingTiny

@Composable
fun DotsIndicator(
    pagerState: PagerState,
    totalPages: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalPages) { index ->
            val isSelected = pagerState.currentPage == index

            Box(
                modifier = Modifier
                    .size(dotSize)
                    .clip(CircleShape)
                    .background(
                        color = if (isSelected)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSecondary
                    )
            )

            Spacer(
                modifier = Modifier.width(spacingTiny)
            )
        }
    }
}
