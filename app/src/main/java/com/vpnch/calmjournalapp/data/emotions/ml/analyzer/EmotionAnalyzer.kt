package com.vpnch.calmjournalapp.data.emotions.ml.analyzer

interface EmotionAnalyzer {

    suspend fun analyzeText(text: String): FloatArray?

    suspend fun initialize()

    fun close()
}