package com.vpnch.calmjournalapp.core.designsystem.components

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
import androidx.compose.ui.unit.dp
import com.vpnch.calmjournalapp.core.designsystem.components.NextButtonDimens.NEXT_BUTTON_HEIGHT
import com.vpnch.calmjournalapp.core.designsystem.components.NextButtonDimens.NEXT_BUTTON_WIDTH

private object NextButtonDimens {
    const val NEXT_BUTTON_WIDTH = 290
    const val NEXT_BUTTON_HEIGHT = 49
}

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
                .width(NEXT_BUTTON_WIDTH.dp)
                .height(NEXT_BUTTON_HEIGHT.dp),
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