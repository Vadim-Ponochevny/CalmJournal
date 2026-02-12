package com.vpnch.calmjournalapp.features.onboarding.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import coil3.compose.AsyncImage
import com.vpnch.calmjournalapp.R
import com.vpnch.calmjournalapp.core.designsystem.Dimens.avatarSize
import com.vpnch.calmjournalapp.core.designsystem.Dimens.avatarAddIconSize
import com.vpnch.calmjournalapp.core.designsystem.Dimens.avatarSelectedBorderWidth

@Composable
fun AvatarItem(
    imageUri: Uri?,
    painter: Painter?,
    isSelected: Boolean,
    isAddButton: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(avatarSize)
            .aspectRatio(1f)
            .clip(CircleShape)
            .clickable { onClick() }
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = avatarSelectedBorderWidth,
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
                } else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {

        when {
            isAddButton -> Icon(
                imageVector = ImageVector.vectorResource(R.drawable.btn_add_avatar),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.size(avatarAddIconSize)
            )

            imageUri != null -> AsyncImage(
                model = imageUri,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            painter != null -> Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}