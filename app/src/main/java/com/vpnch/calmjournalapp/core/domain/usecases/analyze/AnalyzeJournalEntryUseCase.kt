package com.vpnch.calmjournalapp.core.domain.usecases.analyze

import com.vpnch.calmjournalapp.core.domain.models.Emotion
import com.vpnch.calmjournalapp.core.domain.models.EmotionAnalysisResult
import com.vpnch.calmjournalapp.core.domain.repository.EmotionAnalysisRepository
import javax.inject.Inject

class AnalyzeJournalEntryUseCase @Inject constructor(
    private val repository: EmotionAnalysisRepository
) {
    suspend operator fun invoke(text: String): EmotionAnalysisResult {
        return repository.analyzeText(text)
    }

    suspend fun getTopEmotions(text: String, topN: Int = 3): List<Pair<Emotion, Float>> {
        val rawScores = repository.getRawScores(text)
        return rawScores.entries
            .map { Emotion.fromLabel(it.key) to it.value }
            .sortedByDescending { it.second }
            .take(topN)
    }
}