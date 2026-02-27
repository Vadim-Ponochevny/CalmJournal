package com.vpnch.calmjournalapp.presentation.designsystem.theme

import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import com.vpnch.calmjournalapp.R

@OptIn(ExperimentalTextApi::class)
val InterVariableFontFamily = FontFamily(
    // Для обычного текста (400)
    Font(
        resId = R.font.inter_variable,
        weight = FontWeight.Normal, // Это "ключ", по которому Compose ищет шрифт
        variationSettings = FontVariation.Settings(
            FontVariation.weight(400) // Это реальная жирность внутри файла
        )
    ),
    // Для жирного (700)
    Font(
        resId = R.font.inter_variable,
        weight = FontWeight.Bold,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(700)
        )
    ),
    // Для САМОГО жирного лозунга (800 или даже 900)
    Font(
        resId = R.font.inter_variable,
        weight = FontWeight.ExtraBold,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(800)
        )
    )
)