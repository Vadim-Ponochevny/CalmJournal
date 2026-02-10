package com.vpnch.calmjournalapp.core.domain.repository

import com.vpnch.calmjournalapp.core.domain.models.EmotionAnalysisResult

interface EmotionAnalysisRepository {

    suspend fun analyzeText(text: String): EmotionAnalysisResult

    suspend fun getRawScores(text: String): Map<String, Float>

    // Состояние модели
    fun isModelLoaded(): Boolean
    suspend fun initializeModel(): Result<Unit>

    // Настройки
    var detectionThreshold: Float  // порог для определения эмоций (по умолчанию 0.3)
    val availableEmotions: List<String>  // список всех эмоций
}