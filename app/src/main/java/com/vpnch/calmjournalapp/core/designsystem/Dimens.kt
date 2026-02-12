package com.vpnch.calmjournalapp.core.designsystem

import androidx.compose.ui.unit.dp

object Dimens {
    // ================ БАЗОВАЯ СЕТКА ================
    const val GRID_UNIT = 4

    // ================ ОТСТУПЫ (PADDING/SPACING) ================
    val spacingTiny = (GRID_UNIT * 1).dp // 4
    val spacingSmall = (GRID_UNIT * 2).dp // 8
    val spacingSmallRegular = (GRID_UNIT * 3).dp // 12
    val spacingRegular = (GRID_UNIT * 4).dp // 16
    val spacingMedium = (GRID_UNIT * 5).dp // 20
    val spacingLarge = (GRID_UNIT * 6).dp // 24
    val spacingExtraLarge = (GRID_UNIT * 8).dp // 32
    val spacingHuge = (GRID_UNIT * 10).dp // 40
    val spacingExtraHuge = (GRID_UNIT * 12).dp // 48

    // ================ РАЗМЕРЫ КОМПОНЕНТОВ ================
    val contentWidthPercent = 0.8f
    val dialogMinHeight = (GRID_UNIT * 50).dp  // 200dp
    val cornerRadiusLarge = (GRID_UNIT * 6).dp  // 24dp
    val cornerRadiusRegular = (GRID_UNIT * 4).dp  // 16dp

    // ================ АВАТАРЫ ================
    val avatarSize = (GRID_UNIT * 22).dp  // 88dp
    val avatarAddIconSize = (GRID_UNIT * 7).dp  // 28dp
    val avatarSelectedBorderWidth = (GRID_UNIT * 1).dp  // 4dp

    // ================ ИНДИКАТОРЫ ================
    val dotSize = (GRID_UNIT * 2).dp  // 8dp
    val dotSpacing = spacingTiny  // 4dp

    // ================ КНОПКИ ================
    val nextButtonWidth = (GRID_UNIT * 74).dp // 288dp
    val nextButtonHeight = (GRID_UNIT * 12).dp  // 48dp
    val buttonMinWidth = (GRID_UNIT * 20).dp  // 80dp
    val buttonHeight = (GRID_UNIT * 12).dp  // 48dp
    val buttonContentPaddingHorizontal = spacingSmall  // 8dp
    val buttonContentPaddingVertical = spacingSmallRegular  // 12dp

    // ================ ПОЛЯ ВВОДА ================
    val fieldHorizontalPadding = spacingMedium  // 20dp
    val fieldVerticalPadding = spacingSmallRegular  // 12dp
    val fieldMaxWidth = (GRID_UNIT * 80).dp

    // ================ СЕТКИ ================
    val gridPadding = spacingExtraLarge  // 32dp (было 41)
    val gridSpacing = spacingMedium  // 20dp
    const val avatarGridColumns = 3

    // ================ АНИМАЦИЯ ================
    val lottieSize = (GRID_UNIT * 62).dp  // 248dp
    const val animationSpeed = 1.0f

    // ================ КАСТОМНЫЕ ОТСТУПЫ ДЛЯ ЭКРАНОВ ================
    val welcomeAnimationTopPadding = spacingExtraLarge  // 32dp (было 54 - нарушало кратность)
    val welcomeAnimationToTextSpacer = spacingHuge  // 40dp (было 100 - слишком много)
    val welcomeTextVerticalSpacing = spacingMedium  // 20dp (было 18)
}