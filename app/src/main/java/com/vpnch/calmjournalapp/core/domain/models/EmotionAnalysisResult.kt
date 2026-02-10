package com.vpnch.calmjournalapp.core.domain.models

data class EmotionAnalysisResult(
    val emotions: List<EmotionScore>,
    val dominantEmotions: List<Emotion>,
) {
    val confidence: Float
        get() = emotions.maxByOrNull { it.score }?.score ?: 0f
}

data class EmotionScore(
    val emotion: Emotion,
    val score: Float,
    val isAboveThreshold: Boolean = false
)