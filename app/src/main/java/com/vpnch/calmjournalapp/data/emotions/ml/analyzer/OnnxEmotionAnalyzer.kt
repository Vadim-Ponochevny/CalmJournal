package com.vpnch.calmjournalapp.data.emotions.ml.analyzer

import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import android.content.Context
import com.vpnch.calmjournalapp.data.emotions.ml.inference.OnnxInferenceService
import com.vpnch.calmjournalapp.data.emotions.ml.loader.ModelLoader
import com.vpnch.calmjournalapp.data.emotions.ml.tensor.TensorService
import com.vpnch.calmjournalapp.data.emotions.ml.tokenizer.BertTokenizer
import com.vpnch.calmjournalapp.data.emotions.ml.utils.MathUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OnnxEmotionAnalyzer @Inject constructor(
    private val context: Context,
    private val tokenizer: BertTokenizer,
    private val modelLoader: ModelLoader,
) : EmotionAnalyzer {

    private var ortEnvironment: OrtEnvironment? = null
    private var ortSession: OrtSession? = null
    private var tensorService: TensorService? = null
    private var inferenceService: OnnxInferenceService? = null

    override suspend fun initialize() {
        if (ortSession != null) return

        try {
            val (env, session) = modelLoader.loadModel(context)
            ortEnvironment = env
            ortSession = session
            tensorService = TensorService(env)
            inferenceService = OnnxInferenceService(session)
        } catch (e: Exception) {
            throw RuntimeException("Failed to load model: ${e.message}", e)
        }
    }

    override suspend fun analyzeText(text: String): FloatArray? {
        if (ortSession == null) {
            initialize()
        }

        return withContext(Dispatchers.IO) {
            try {
                // 1. Токенизация
                val (inputIds, attentionMask, tokenTypeIds) = tokenizer.tokenize(text)

                // 2. Подготовка тензоров
                val tensors = tensorService!!.prepareInputTensors(
                    inputIds, attentionMask, tokenTypeIds
                )

                // 3. Инференс
                val logits = inferenceService!!.runInference(tensors)

                // 4. Преобразование в вероятности
                if (logits.isNotEmpty()) {
                    MathUtils.applySigmoidToArray(logits)
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    override fun close() {
        ortSession?.close()
        ortEnvironment = null
        ortSession = null
        tensorService = null
        inferenceService = null
    }
}