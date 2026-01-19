package com.vpnch.calmjournalapp.features.onboarding.pages

import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.vpnch.calmjournalapp.R

object WelcomeAnimationDimens {
    const val ANIMATION_TOP_PADDING = 54
    const val ANIMATION_SIZE = 250
    const val TEXT_SECTION_WIDTH = 300
    const val ANIMATION_TO_TEXT_SPACER = 100
    const val TEXT_VERTICAL_SPACING = 18
}

@Composable
fun WelcomeAnimationPage () {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(R.raw.welcome_animation)
        )
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            speed = 1.0f,
            isPlaying = true,
            restartOnPlay = true
        )
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .padding(
                    top = WelcomeAnimationDimens.ANIMATION_TOP_PADDING.dp
                ).size(WelcomeAnimationDimens.ANIMATION_SIZE.dp)
        )

        Spacer(modifier = Modifier.Companion.height(WelcomeAnimationDimens.ANIMATION_TO_TEXT_SPACER.dp))

        Column(
            modifier = Modifier.Companion.width(WelcomeAnimationDimens.TEXT_SECTION_WIDTH.dp)
        ) {
            Text(
                text = stringResource(R.string.welcome_title),
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.Companion.height(WelcomeAnimationDimens.TEXT_VERTICAL_SPACING.dp))

            Text(
                text = stringResource(R.string.welcome_subtitle),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}