package com.vpnch.calmjournalapp.features.onboarding.pages

import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vpnch.calmjournalapp.R
import coil3.compose.AsyncImage
import androidx.core.net.toUri
import com.vpnch.calmjournalapp.features.onboarding.utils.BitmapTempSaver.saveBitmapToTempFile

@Composable
fun AvatarSelectionPage(
    modifier: Modifier = Modifier,
    selectedUri: Uri?,
    selectedDefaultAvatar: Int?,
    onCustomAvatarSelected: (Uri?) -> Unit,
    onDefaultAvatarSelected: (Int) -> Unit,
    defaultAvatars: List<Int> = listOf(
        R.drawable.avatar_badboy,
        R.drawable.avatar_emo,
        R.drawable.avatar_softgirl,
        R.drawable.avatar_women,
    )
) {

    var showCameraGalleryDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            onCustomAvatarSelected(uri)
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            bitmap?.let {
                val tempUri = saveBitmapToTempFile(context, it)
                if (tempUri != null) {
                    onCustomAvatarSelected(tempUri)
                } else {
                    Toast.makeText(context, "Ошибка с кэшем", Toast.LENGTH_SHORT).show()
                }
            }
        }
    )

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.width(335.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Фотография профиля",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Выберите из предложенных или загрузите из галереи",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Button "Add photo from gallery or camera"
            item {
                val hasCustomAvatar = selectedUri?.let { true } ?: false

                AvatarItem(
                    imageUri = selectedUri,
                    painter = null,
                    isSelected = hasCustomAvatar,
                    isAddButton = !hasCustomAvatar,
                    onClick = {
                        showCameraGalleryDialog = true
                    }
                )
            }
            // Default avatars
            defaultAvatars.forEach { avatar ->
                item {
                    val isSelected = selectedDefaultAvatar == avatar

                    AvatarItem(
                        imageUri = null,
                        painter = painterResource(avatar),
                        isSelected = isSelected,
                        isAddButton = false,
                        onClick = {
                            onDefaultAvatarSelected(avatar)
                        }
                    )
                }
            }
        }


        if (showCameraGalleryDialog) {
            AlertDialog(
                onDismissRequest = { showCameraGalleryDialog = false },
                title = { Text("Выбрать фото") },
                text = { Text("Откуда вы хотить взять фото?") },
                confirmButton = {
                    TextButton(onClick = {
                        showCameraGalleryDialog = false
                        cameraLauncher.launch(null)
                    }) {
                        Text("Камера")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showCameraGalleryDialog = false
                        galleryLauncher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }) {
                        Text("Галерея")
                    }
                }
            )
        }
    }
}

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
            .size(100.dp)
            .clip(CircleShape)
            .clickable { onClick() }
            .border(
                width = if (isSelected) 4.dp else 2.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.outline,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        when {
            isAddButton -> Icon(
                imageVector = ImageVector.vectorResource(R.drawable.btn_add_avatar),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
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


