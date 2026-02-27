package com.vpnch.calmjournalapp.presentation.onboarding.components

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
import com.vpnch.calmjournalapp.R
import com.vpnch.calmjournalapp.presentation.designsystem.Dimens.cornerRadiusLarge
import com.vpnch.calmjournalapp.presentation.designsystem.Dimens.dialogMinHeight
import com.vpnch.calmjournalapp.presentation.designsystem.Dimens.spacingLarge
import com.vpnch.calmjournalapp.presentation.designsystem.Dimens.spacingSmall
import com.vpnch.calmjournalapp.presentation.designsystem.Dimens.spacingSmallRegular

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
            shape = RoundedCornerShape(cornerRadiusLarge),
            color = MaterialTheme.colorScheme.surfaceVariant,
            tonalElevation = 0.dp
        ) {
            Column(
                modifier = Modifier.padding(spacingLarge)
                    .defaultMinSize(minHeight = dialogMinHeight)
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

                    Spacer(modifier = Modifier.height(spacingLarge))

                    Button(
                        onClick = {
                            onCameraClick()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(cornerRadiusLarge),
                        contentPadding = PaddingValues(spacingSmallRegular),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Spacer(Modifier.width(spacingSmall))
                        Text(stringResource(R.string.onboarding_camera_button))
                    }

                    Spacer(modifier = Modifier.height(spacingSmallRegular))

                    Button(
                        onClick = {
                            onGalleryClick()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(cornerRadiusLarge),
                        contentPadding = PaddingValues(spacingSmallRegular),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Spacer(Modifier.width(spacingSmallRegular))
                        Text(stringResource(R.string.onboarding_gallery_button))
                    }
                }
            }
        }
    }
}