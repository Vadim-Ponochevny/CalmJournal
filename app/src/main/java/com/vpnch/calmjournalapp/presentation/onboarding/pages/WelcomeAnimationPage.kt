package com.vpnch.calmjournalapp.presentation.onboarding.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vpnch.calmjournalapp.R
import com.vpnch.calmjournalapp.presentation.designsystem.Dimens
import com.vpnch.calmjournalapp.presentation.onboarding.components.WelcomeLottieAnimation

object WelcomeAnimationDimens {
    const val TEXT_SECTION_WIDTH = 300
    const val ANIMATION_TO_TEXT_SPACER = 100
}

@Composable
fun WelcomeAnimationPage () {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        WelcomeLottieAnimation()

        Spacer(modifier = Modifier.Companion.height(WelcomeAnimationDimens.ANIMATION_TO_TEXT_SPACER.dp))

        Column(
            modifier = Modifier.Companion.width(WelcomeAnimationDimens.TEXT_SECTION_WIDTH.dp)
        ) {
            Text(
                text = stringResource(R.string.onboarding_welcome_title),
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.Companion.height(Dimens.spacingRegular))

            Text(
                text = stringResource(R.string.onboarding_welcome_subtitle),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}