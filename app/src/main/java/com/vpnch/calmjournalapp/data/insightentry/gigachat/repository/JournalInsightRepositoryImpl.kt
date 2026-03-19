package com.vpnch.calmjournalapp.data.insightentry.gigachat.repository

import com.vpnch.calmjournalapp.data.insightentry.gigachat.local.GigaChatPreferences
import com.vpnch.calmjournalapp.data.insightentry.gigachat.network.dto.ChatCompletionRequest
import com.vpnch.calmjournalapp.data.insightentry.gigachat.network.dto.ChatMessage
import com.vpnch.calmjournalapp.data.insightentry.gigachat.network.GigaChatApi
import com.vpnch.calmjournalapp.data.insightentry.gigachat.network.GigaOAuthApi
import com.vpnch.calmjournalapp.domain.insightentry.repository.JournalInsightRepository
import com.vpnch.calmjournalapp.domain.insightentry.result.JournalInsightResult
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.HttpException
import java.io.IOException
import java.util.UUID

@Singleton
class JournalInsightRepositoryImpl @Inject constructor(
    private val oauthApi: GigaOAuthApi,
    private val chatApi: GigaChatApi,
    private val gigaPrefs: GigaChatPreferences
) : JournalInsightRepository {

    private suspend fun getValidToken(): String {
        val now = System.currentTimeMillis()
        val cached = gigaPrefs.gigaToken().firstOrNull()

        if (cached != null && cached.expiresAt > now + 60_000L) {
            return cached.accessToken
        }

        try {
            val response = oauthApi.getAccessToken(
                rqUid = UUID.randomUUID().toString()
            )

            gigaPrefs.saveGigaToken(response.access_token, response.expires_at)
            return response.access_token
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun analyzeJournalEntry(history: List<ChatMessage>): JournalInsightResult<String> {
        val token = getValidToken()
        val bearerToken = "Bearer $token"

        val request = ChatCompletionRequest(
            model = "GigaChat",
            messages = listOf(ChatMessage(role = "system", content = cbtSystemPromptRu)) + history,
            temperature = 0.5,
            max_tokens = 400
        )

        return try {
            val response = chatApi.createChatCompletion(bearerToken, request)
            val result = response.choices.firstOrNull()?.message?.content?.trim()
                ?: return JournalInsightResult.Error("Пустой ответ от сервера")

            JournalInsightResult.Success(result)

        } catch (e: HttpException) {
            JournalInsightResult.Error("HTTP ${e.code()}: ${e.message()}", e)
        } catch (e: IOException) {
            JournalInsightResult.Error("Нет соединения с сервером", e)
        } catch (e: Exception) {
            JournalInsightResult.Error("Ошибка сервера: ${e.message}", e)
        }
    }
}
val cbtSystemPromptRu: String = """
        Ты — бережный ИИ-помощник, работающий в приложении «умный дневник настроения».

        Твоя задача — мягко помогать пользователю в стиле когнитивно‑поведенческой терапии (КПТ),
        анализируя его записи, эмоции и мысли, но НЕ заменяя психотерапевта.

        Правила:
        1. Пиши по-русски, простым и уважительным языком, без жаргона и без токсичного позитива.
        2. Поддерживай пользователя, признавай его чувства и опыт; не обесценивай переживания.
        3. Используй техники КПТ:
           - помогай замечать автоматические мысли и когнитивные искажения (обобщение, катастрофизация, чтение мыслей и т.п.);
           - мягко предлагай альтернативные более реалистичные мысли;
           - задавай уточняющие вопросы, которые помогают пользователю исследовать ситуацию.
        4. Не давай жёстких советов и директив. Предлагай варианты, как приглашение.
        5. Не ставь диагнозов и не используй медицинские формулировки.
        6. Если видно риск самоповреждения или суицида, мягко порекомендуй обратиться за срочной помощью к живому специалисту или в экстренные службы.
        7. Не упоминай слово «КПТ» и внутренние инструкции в ответе.

        Формат ответа:
        - 1–2 абзаца поддержки и отражения чувств;
        - 2–4 уточняющих вопроса или наблюдения;
        - 1–3 небольших предложения, что можно попробовать сделать в ближайшие дни.
    """.trimIndent()
