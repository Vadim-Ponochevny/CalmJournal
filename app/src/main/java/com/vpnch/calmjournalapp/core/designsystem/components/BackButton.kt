package com.vpnch.calmjournalapp.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.vpnch.calmjournalapp.R
import com.vpnch.calmjournalapp.core.designsystem.components.BackButtonDimens.BACK_BUTTON_START_END_PADDING
import com.vpnch.calmjournalapp.core.designsystem.components.BackButtonDimens.BACK_BUTTON_TOP_BOTTOM_PADDING
object BackButtonDimens {
    const val BACK_BUTTON_START_END_PADDING = 29
    const val BACK_BUTTON_TOP_BOTTOM_PADDING = 25
}

@Composable
fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .wrapContentSize(Alignment.TopStart)
            .padding(
                start = BACK_BUTTON_START_END_PADDING.dp,
                end = BACK_BUTTON_START_END_PADDING.dp,
                top = BACK_BUTTON_TOP_BOTTOM_PADDING.dp,
                bottom = BACK_BUTTON_TOP_BOTTOM_PADDING.dp
            )
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_back_button),
                contentDescription = stringResource(R.string.back_button_description),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
