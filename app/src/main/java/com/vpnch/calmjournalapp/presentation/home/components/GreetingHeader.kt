package com.vpnch.calmjournalapp.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vpnch.calmjournalapp.domain.user.model.User
import com.vpnch.calmjournalapp.presentation.home.utils.AvatarModel
import com.vpnch.calmjournalapp.presentation.home.utils.parseAvatarData

@Composable
fun GreetingHeader(
    user: User?,
    modifier: Modifier = Modifier
) {
    val displayName by remember(user?.name) {
        mutableStateOf<String>(
            user?.name?.takeIf { it.isNotBlank() } ?: "пользователь"
        )
    }

    val avatarModel = remember(user?.avatarData) {
        parseAvatarData(user?.avatarData)
    }

    Row(
        modifier = modifier.padding(start = 24.dp, end = 24.dp, top = 40.dp, bottom = 32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            when (avatarModel) {
                is AvatarModel.Custom -> {
                    AsyncImage(
                        model = avatarModel.uri,
                        contentDescription = null,
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape),
                        error = null,
                    )
                }

                is AvatarModel.Default -> {
                    Image(
                        painter = painterResource(avatarModel.resId),
                        contentDescription = null,
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                    )
                }

                null -> {
                    Text(
                        text = displayName.firstOrNull()?.uppercase() ?: "?",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
        }

        Spacer(Modifier.width(12.dp))

        Text(
            text = "${displayName}, привет!",
            style = MaterialTheme.typography.titleMedium,
        )
    }
}
