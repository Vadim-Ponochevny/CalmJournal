package com.vpnch.calmjournalapp.features.onboarding.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.vpnch.calmjournalapp.R
import com.vpnch.calmjournalapp.core.designsystem.Dimens.lottieSize
import com.vpnch.calmjournalapp.core.designsystem.Dimens.spacingExtraHuge

private const val SPEED_OF_ANIMATION = 1.0f

@Composable
fun WelcomeLottieAnimation() {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.welcome_animation)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        speed = SPEED_OF_ANIMATION,
        isPlaying = true,
        restartOnPlay = true
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier
            .padding(
                top = spacingExtraHuge
            ).size(lottieSize)
    )
}