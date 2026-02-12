package com.vpnch.calmjournalapp.core.data.ml.emotion.analyzer

interface EmotionAnalyzer {

    suspend fun analyzeText(text: String): FloatArray?

    suspend fun initialize()

    fun close()
}