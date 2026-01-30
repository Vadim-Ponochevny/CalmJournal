package com.vpnch.calmjournalapp.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    // Онбординг:
    displaySmall = TextStyle(
        fontFamily = InterVariableFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),

    // Онбординг: подпись + экраны 2-3 описание
    bodyLarge = TextStyle(
        fontFamily = InterVariableFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),

    // Экраны 2-3: "Как тебя зовут?"
    headlineSmall = TextStyle(
        fontFamily = InterVariableFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 26.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),

    // Экраны 2-3: описание
    titleLarge = TextStyle(
        fontFamily = InterVariableFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 32.sp,
    ),

    // Главный: "Привет, пользователь!"
    titleMedium = TextStyle(
        fontFamily = InterVariableFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp
    ),

    // Главный: лозунг
    headlineMedium = TextStyle(
        fontFamily = InterVariableFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),

    // "Начать запись"
    titleSmall = TextStyle(
        fontFamily = InterVariableFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.25.sp
    ),

    // "Последние записи", "Вчера", заголовки записей
    bodyMedium = TextStyle(
        fontFamily = InterVariableFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,  // 20/15/18 сп
        lineHeight = 22.sp,
        letterSpacing = 0.25.sp
    ),

    // Текст заметки
    bodySmall = TextStyle(
        fontFamily = InterVariableFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.25.sp
    ),

    // ИИ подпись + статистика
    labelLarge = TextStyle(
        fontFamily = InterVariableFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.25.sp
    ),

    // Статистика: заголовок "Итоги месяца"
    headlineLarge = TextStyle(
        fontFamily = InterVariableFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),

    // Статистика: подписи, выбор месяца
    labelMedium = TextStyle(
        fontFamily = InterVariableFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.25.sp
    ),

    // Категория, итоги, "показать все"
    labelSmall = TextStyle(
        fontFamily = InterVariableFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.25.sp
    )
)