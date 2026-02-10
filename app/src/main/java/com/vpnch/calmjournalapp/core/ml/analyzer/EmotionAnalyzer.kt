package com.vpnch.calmjournalapp.core.ml.analyzer

interface EmotionAnalyzer {

    suspend fun analyzeText(text: String): FloatArray?

    suspend fun initialize()

    fun close()
}