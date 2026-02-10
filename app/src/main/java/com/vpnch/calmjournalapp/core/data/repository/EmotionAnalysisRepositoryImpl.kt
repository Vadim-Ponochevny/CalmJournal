package com.vpnch.calmjournalapp.core.data.repository

import android.util.Log
import com.vpnch.calmjournalapp.core.domain.models.Emotion
import com.vpnch.calmjournalapp.core.domain.models.EmotionAnalysisResult
import com.vpnch.calmjournalapp.core.domain.models.EmotionScore
import com.vpnch.calmjournalapp.core.domain.repository.EmotionAnalysisRepository
import com.vpnch.calmjournalapp.core.data.manager.OnnxModelManager
import javax.inject.Inject

class EmotionAnalysisRepositoryImpl @Inject constructor(
    private val onnxModelManager: OnnxModelManager
) : EmotionAnalysisRepository {

    override var detectionThreshold: Float = 0.3f

    override val availableEmotions: List<String> = listOf(
        "admiration", "amusement", "anger", "annoyance", "approval",
        "caring", "confusion", "curiosity", "desire", "disappointment",
        "disapproval", "disgust", "embarrassment", "excitement", "fear",
        "gratitude", "grief", "joy", "love", "nervousness",
        "optimism", "pride", "realization", "relief", "remorse",
        "sadness", "surprise", "neutral"
    )

    override suspend fun analyzeText(text: String): EmotionAnalysisResult {
        val rawScores = getRawScores(text)

        val emotionScores = rawScores.map { (label, score) ->
            val emotion = Emotion.fromLabel(label)
            EmotionScore(
                emotion = emotion,
                score = score,
                isAboveThreshold = score >= detectionThreshold
            )
        }.sortedByDescending { it.score }

        val dominantEmotions = emotionScores
            .filter { it.isAboveThreshold }
            .map { it.emotion }


        return EmotionAnalysisResult(
            emotions = emotionScores,
            dominantEmotions = dominantEmotions,
        )
    }

    override suspend fun getRawScores(text: String): Map<String, Float> {
        val rawOutput = onnxModelManager.analyzeText(text) ?: return emptyMap()

        return availableEmotions.mapIndexed { index, emotionLabel ->
            emotionLabel to rawOutput.getOrElse(index) { 0f }
        }.toMap()
    }

    override fun isModelLoaded(): Boolean {
        return onnxModelManager.isModelLoaded
    }

    override suspend fun initializeModel(): Result<Unit> {
        return try {
            onnxModelManager.initialize()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("EmotionAnalysisRepositoryImpl", "error with initializeModel $e")
            Result.failure(e)
        }
    }
}