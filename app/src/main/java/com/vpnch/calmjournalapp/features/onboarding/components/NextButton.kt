package com.vpnch.calmjournalapp.features.onboarding.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vpnch.calmjournalapp.core.designsystem.Dimens.nextButtonHeight
import com.vpnch.calmjournalapp.core.designsystem.Dimens.nextButtonWidth

@Composable
fun NextButton(
    text: String,
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onNext,
            modifier = modifier
                .width(nextButtonWidth)
                .height(nextButtonHeight),
            enabled = enabled,
        ) {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.background,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}