package com.vpnch.calmjournalapp.presentation.onboarding.pages

import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.vpnch.calmjournalapp.R
import com.vpnch.calmjournalapp.presentation.designsystem.Dimens.contentColumnWidth
import com.vpnch.calmjournalapp.presentation.designsystem.Dimens.contentMaxWidth
import com.vpnch.calmjournalapp.presentation.designsystem.Dimens.gridPadding
import com.vpnch.calmjournalapp.presentation.designsystem.Dimens.gridSpacing
import com.vpnch.calmjournalapp.presentation.designsystem.Dimens.spacingSmall
import com.vpnch.calmjournalapp.presentation.onboarding.AvatarState
import com.vpnch.calmjournalapp.presentation.onboarding.AvatarType
import com.vpnch.calmjournalapp.presentation.onboarding.components.AvatarItem
import com.vpnch.calmjournalapp.presentation.onboarding.components.CameraGalleryDialog
import com.vpnch.calmjournalapp.presentation.onboarding.utils.BitmapTempSaver.saveBitmapToTempFile
import com.vpnch.calmjournalapp.presentation.onboarding.utils.BitmapTempSaver.clearOldAvatars

private const val GRID_COUNT_OF_COLUMNS = 3

@Composable
fun AvatarSelectionPage(
    modifier: Modifier = Modifier,
    avatarState: AvatarState,
    onCustomAvatarSelected: (Uri?) -> Unit,
    onDefaultAvatarSelected: (Int) -> Unit,
    defaultAvatars: List<Int> = listOf(
        R.drawable.avatar_badboy,
        R.drawable.avatar_emo,
        R.drawable.avatar_softgirl,
        R.drawable.avatar_women,
    )
) {
    val context = LocalContext.current

    var showCameraGalleryDialog by rememberSaveable { mutableStateOf(false) }
    var isLoadingInDialog by rememberSaveable { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            showCameraGalleryDialog = false
            isLoadingInDialog = false
            uri?.let {
                onCustomAvatarSelected(it)
            }
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            showCameraGalleryDialog = false
            isLoadingInDialog = false
            bitmap?.let {
                clearOldAvatars(context)
                val tempUri = saveBitmapToTempFile(context, it)
                if (tempUri != null) {
                    onCustomAvatarSelected(tempUri)
                } else {
                    Toast.makeText(
                        context,
                        R.string.onboarding_cache_error,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    )

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            modifier = Modifier.width(contentColumnWidth),
        ) {
            Text(
                text = stringResource(R.string.onboarding_avatar_page_title),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(spacingSmall))

            Text(
                text = stringResource(R.string.onboarding_avatar_page_subtitle),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(spacingSmall))
        }

        LazyVerticalGrid(
            modifier = Modifier.widthIn(max = contentMaxWidth),
            columns = GridCells.Fixed(GRID_COUNT_OF_COLUMNS),
            contentPadding = PaddingValues(gridPadding),
            horizontalArrangement = Arrangement.spacedBy(gridSpacing),
            verticalArrangement = Arrangement.spacedBy(gridSpacing)
        ) {
            // Button "Add photo from gallery or camera"
            item {
                AvatarItem(
                    imageUri = avatarState.uri,
                    painter = null,
                    isSelected = false,
                    isAddButton = true,
                    onClick = {
                        showCameraGalleryDialog = true
                    }
                )
            }
            // custom avatar
            if (avatarState.uri != null) {
                item {
                    val isSelected = avatarState.selectedType == AvatarType.CUSTOM &&
                            avatarState.uri != null
                    AvatarItem(
                        imageUri = avatarState.uri,
                        painter = null,
                        isSelected = isSelected,
                        isAddButton = false,
                        onClick = {
                            onCustomAvatarSelected(avatarState.uri)
                        }
                    )
                }
            }

            // default avatars
            defaultAvatars.forEach { avatar ->
                item {
                    val isSelected = avatarState.selectedType == AvatarType.DEFAULT_AVATAR &&
                            avatarState.resId == avatar
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
            CameraGalleryDialog(
                onDismiss = { showCameraGalleryDialog = false },
                isLoading = isLoadingInDialog,
                onCameraClick = {
                    isLoadingInDialog = true
                    cameraLauncher.launch(null)
                },
                onGalleryClick = {
                    isLoadingInDialog = true
                    galleryLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }
            )
        }
    }
}



