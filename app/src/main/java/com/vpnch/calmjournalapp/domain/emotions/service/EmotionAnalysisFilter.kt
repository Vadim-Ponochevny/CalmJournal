package com.vpnch.calmjournalapp.domain.emotions.service

import com.vpnch.calmjournalapp.domain.emotions.models.Emotion
import com.vpnch.calmjournalapp.domain.emotions.models.EmotionAnalysisResult

/**
 * Фильтр для определения, нужно ли анализировать заметку с помощью AI
 */
object EmotionAnalysisFilter {

    // Уровень чувствительности анализа (можно сделать настраиваемым)
    enum class Sensitivity {
        LOW,    // только критические состояния
        MEDIUM, // средний уровень (рекомендуется)
        HIGH    // почти всё что не нейтральное
    }

    // Текущий уровень чувствительности (по умолчанию MEDIUM-HIGH)
    var currentSensitivity: Sensitivity = Sensitivity.MEDIUM

    // Критические эмоции (требуют внимания)
    private val criticalEmotions = setOf(
        Emotion.SADNESS,      // грусть
        Emotion.GRIEF,        // горе
        Emotion.REMORSE,      // раскаяние
        Emotion.FEAR,         // страх
        Emotion.ANGER,        // злость
        Emotion.DISGUST,      // отвращение
        Emotion.GRIEF       // отчаяние
    )

    // Эмоции, требующие поддержки
    private val supportNeededEmotions = setOf(
        Emotion.CONFUSION,    // непонимание
        Emotion.NERVOUSNESS,  // нервозность
        Emotion.EMBARRASSMENT, // смущение
        Emotion.DISAPPOINTMENT, // разочарование
        Emotion.DISAPPROVAL,   // неодобрение
        Emotion.ANNOYANCE      // раздражение
    )

    // Пороговые значения
    object Thresholds {
        const val HIGH_SADNESS = 0.35f        // высокая грусть
        const val HIGH_ANGER = 0.35f          // высокая злость
        const val CRITICAL_NEGATIVE_SUM = 0.6f // суммарный негатив
        const val VERY_LOW_EMOTION = 0.2f     // всё приглушено (возможно депрессия)
        const val DOMINANT_THRESHOLD = 0.3f   // порог доминирующей эмоции
    }

    /**
     * Основная функция фильтрации
     * @return true - нужно отправлять на анализ AI, false - не нужно
     */
    fun shouldAnalyzeWithAI(result: EmotionAnalysisResult): Boolean {
        return when (currentSensitivity) {
            Sensitivity.LOW -> checkLowSensitivity(result)
            Sensitivity.MEDIUM -> checkMediumSensitivity(result)
            Sensitivity.HIGH -> checkHighSensitivity(result)
        }
    }

    /**
     * Низкая чувствительность: только критические состояния
     */
    private fun checkLowSensitivity(result: EmotionAnalysisResult): Boolean {
        // 1. Критические доминирующие эмоции
        if (result.dominantEmotions.any { it in criticalEmotions }) {
            return true
        }

        // 2. Очень сильная грусть или злость
        val sadnessScore = result.getScoreForEmotion(Emotion.SADNESS)
        val angerScore = result.getScoreForEmotion(Emotion.ANGER)

        if (sadnessScore > 0.5f || angerScore > 0.5f) {
            return true
        }

        // 3. Суицидальные индикаторы (особая проверка)
        if (hasSuicidalIndicators(result)) {
            return true
        }

        return false
    }

    /**
     * Средняя чувствительность (рекомендуется)
     */
    private fun checkMediumSensitivity(result: EmotionAnalysisResult): Boolean {
        // 1. Критические или нуждающиеся в поддержке доминирующие эмоции
        val relevantEmotions = criticalEmotions + supportNeededEmotions
        if (result.dominantEmotions.any { it in relevantEmotions }) {
            return true
        }

        // 2. Высокие значения негативных эмоций (даже если не доминируют)
        val sadnessScore = result.getScoreForEmotion(Emotion.SADNESS)
        val angerScore = result.getScoreForEmotion(Emotion.ANGER)
        val fearScore = result.getScoreForEmotion(Emotion.FEAR)

        if (sadnessScore > Thresholds.HIGH_SADNESS ||
            angerScore > Thresholds.HIGH_ANGER ||
            fearScore > 0.3f) {
            return true
        }

        // 3. Суммарный негатив
        val negativeSum = result.emotions
            .filter { it.emotion in criticalEmotions }
            .sumOf { it.score.toDouble() }
            .toFloat()

        if (negativeSum > Thresholds.CRITICAL_NEGATIVE_SUM) {
            return true
        }

        // 4. Очень приглушённые эмоции (возможно депрессия)
        if (hasVeryLowEmotions(result)) {
            return true
        }

        // 5. Если в топ-3 есть тревожные эмоции
        val top3Emotions = result.emotions
            .sortedByDescending { it.score }
            .take(3)
            .map { it.emotion }

        if (top3Emotions.any { it in relevantEmotions }) {
            return true
        }

        return false
    }

    /**
     * Высокая чувствительность: почти всё что не нейтральное
     */
    private fun checkHighSensitivity(result: EmotionAnalysisResult): Boolean {
        // 1. Любая доминирующая эмоция кроме нейтральной
        if (result.dominantEmotions.isNotEmpty() &&
            !result.dominantEmotions.contains(Emotion.NEUTRAL)) {
            return true
        }

        // 2. Если нейтральная, но с низкой уверенностью (< 50%)
        val neutralScore = result.getScoreForEmotion(Emotion.NEUTRAL)
        if (result.dominantEmotions.contains(Emotion.NEUTRAL) && neutralScore < 0.5f) {
            return true
        }

        // 3. Любая эмоция выше 25%
        if (result.emotions.any { it.score > 0.25f && it.emotion != Emotion.NEUTRAL }) {
            return true
        }

        return false
    }

    /**
     * Проверка на суицидальные индикаторы
     */
    private fun hasSuicidalIndicators(result: EmotionAnalysisResult): Boolean {
        // Комбинация эмоций может указывать на суицидальные мысли
        val sadness = result.getScoreForEmotion(Emotion.SADNESS)
        val grief = result.getScoreForEmotion(Emotion.GRIEF)
        val hopelessness = result.getScoreForEmotion(Emotion.DISAPPOINTMENT) +
                result.getScoreForEmotion(Emotion.DISAPPROVAL)

        // Если есть глубокая грусть + безнадёжность
        return (sadness > 0.4f && grief > 0.3f) ||
                (sadness > 0.3f && hopelessness > 0.5f)
    }

    /**
     * Проверка на очень приглушённые эмоции (возможная депрессия)
     */
    private fun hasVeryLowEmotions(result: EmotionAnalysisResult): Boolean {
        // Если все эмоции очень слабые (максимум < 20%)
        val maxScore = result.emotions.maxOfOrNull { it.score } ?: 0f
        if (maxScore < Thresholds.VERY_LOW_EMOTION) {
            return true
        }

        // Если нет доминирующих эмоций (все < 30%)
        if (result.dominantEmotions.isEmpty()) {
            // Проверяем распределение - если всё равномерно низкое
            val averageScore = result.emotions.map { it.score }.average().toFloat()
            return averageScore < 0.15f
        }

        return false
    }

    /**
     * Вспомогательная функция для получения скора конкретной эмоции
     */
    private fun EmotionAnalysisResult.getScoreForEmotion(emotion: Emotion): Float {
        return emotions.find { it.emotion == emotion }?.score ?: 0f
    }

    /**
     * Быстрая проверка для UI (можно показывать значок "нужен анализ")
     */
    fun needsAnalysisIcon(result: EmotionAnalysisResult): Boolean {
        return shouldAnalyzeWithAI(result)
    }

    /**
     * Получить список эмоций для отображения в UI как "требующие внимания"
     */
    fun getEmotionsRequiringAttention(result: EmotionAnalysisResult): List<Emotion> {
        return result.emotions
            .filter {
                (it.emotion in criticalEmotions && it.score > 0.25f) ||
                        (it.emotion in supportNeededEmotions && it.score > 0.3f)
            }
            .map { it.emotion }
    }

    /**
     * Получить уровень срочности анализа (для приоритизации)
     */
    fun getAnalysisUrgency(result: EmotionAnalysisResult): UrgencyLevel {
        return when {
            // Критический уровень
            hasSuicidalIndicators(result) -> UrgencyLevel.CRITICAL

            // Высокий уровень
            result.dominantEmotions.any { it in criticalEmotions } -> UrgencyLevel.HIGH

            // Средний уровень
            result.dominantEmotions.any { it in supportNeededEmotions } -> UrgencyLevel.MEDIUM

            // Низкий уровень
            result.emotions.any { it.score > 0.25f && it.emotion != Emotion.NEUTRAL } ->
                UrgencyLevel.LOW

            else -> UrgencyLevel.NONE
        }
    }

    enum class UrgencyLevel {
        NONE,       // не требуется
        LOW,        // можно проанализировать позже
        MEDIUM,     // желательно проанализировать
        HIGH,       // нужно проанализировать
        CRITICAL    // требуется срочный анализ
    }
}