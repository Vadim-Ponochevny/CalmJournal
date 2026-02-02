package com.vpnch.calmjournalapp.features.onboarding.components

import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.vpnch.calmjournalapp.R
import com.vpnch.calmjournalapp.features.onboarding.components.CameraGalleryDialogDimens.BUTTON_CONTENT_PADDING_HORIZONTAL
import com.vpnch.calmjournalapp.features.onboarding.components.CameraGalleryDialogDimens.BUTTON_CONTENT_PADDING_VERTICAL
import com.vpnch.calmjournalapp.features.onboarding.components.CameraGalleryDialogDimens.DIALOG_CORNER_RADIUS
import com.vpnch.calmjournalapp.features.onboarding.components.CameraGalleryDialogDimens.DIALOG_MIN_HEIGHT
import com.vpnch.calmjournalapp.features.onboarding.components.CameraGalleryDialogDimens.DIALOG_PADDING

private object CameraGalleryDialogDimens {
    const val DIALOG_CORNER_RADIUS = 25
    const val DIALOG_PADDING = 24
    const val DIALOG_MIN_HEIGHT = 200
    const val BUTTON_CONTENT_PADDING_HORIZONTAL = 8
    const val BUTTON_CONTENT_PADDING_VERTICAL = 12
}

@Composable
fun CameraGalleryDialog(
    onDismiss: () -> Unit,
    isLoading: Boolean,
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit,
) {
    Dialog(
        onDismissRequest = {onDismiss()}
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(DIALOG_CORNER_RADIUS.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            tonalElevation = 0.dp
        ) {
            Column(
                modifier = Modifier.padding(DIALOG_PADDING.dp)
                    .defaultMinSize(minHeight = DIALOG_MIN_HEIGHT.dp)
                    .animateContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (isLoading) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    Text(
                        text = stringResource(R.string.onboarding_camera_gallery_dialog_title),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    Spacer(modifier = Modifier.height(DIALOG_PADDING.dp))

                    Button(
                        onClick = {
                            onCameraClick()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(DIALOG_CORNER_RADIUS.dp),
                        contentPadding = PaddingValues(BUTTON_CONTENT_PADDING_VERTICAL.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Spacer(Modifier.width(BUTTON_CONTENT_PADDING_HORIZONTAL.dp))
                        Text(stringResource(R.string.onboarding_camera_button))
                    }

                    Spacer(modifier = Modifier.height(BUTTON_CONTENT_PADDING_VERTICAL.dp))

                    Button(
                        onClick = {
                            onGalleryClick()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(DIALOG_CORNER_RADIUS.dp),
                        contentPadding = PaddingValues(BUTTON_CONTENT_PADDING_VERTICAL.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Spacer(Modifier.width(BUTTON_CONTENT_PADDING_HORIZONTAL.dp))
                        Text(stringResource(R.string.onboarding_gallery_button))
                    }
                }
            }
        }
    }
}