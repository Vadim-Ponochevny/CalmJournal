package com.vpnch.calmjournalapp.domain.models

enum class Emotion(
    val displayName: String
) {
    ADMIRATION("Восхищение"),
    AMUSEMENT("Развлечение"),
    ANGER("Гнев"),
    ANNOYANCE("Раздражение"),
    APPROVAL("Одобрение"),
    CARING("Забота"),
    CONFUSION("Непонимание"),
    CURIOSITY("Любопытство"),
    DESIRE("Желание"),
    DISAPPOINTMENT("Разочарование"),
    DISAPPROVAL("Неодобрение"),
    DISGUST("Отвращение"),
    EMBARRASSMENT("Смущение"),
    EXCITEMENT("Возбуждение"),
    FEAR("Страх"),
    GRATITUDE("Благодарность"),
    GRIEF("Горе"),
    JOY("Радость"),
    LOVE("Любовь"),
    NERVOUSNESS("Нервозность"),
    OPTIMISM("Оптимизм"),
    PRIDE("Гордость"),
    REALIZATION("Осознание"),
    RELIEF("Облегчение"),
    REMORSE("Раскаяние"),
    SADNESS("Грусть"),
    SURPRISE("Удивление"),
    NEUTRAL("Нейтрально"),
    UNKNOWN("Неизвестно");

    companion object {
        fun fromIndex(index: Int): Emotion {
            return entries.getOrNull(index) ?: UNKNOWN
        }

        fun fromLabel(label: String): Emotion {
            return entries.find { it.name.lowercase() == label.lowercase() } ?: UNKNOWN
        }
    }
}