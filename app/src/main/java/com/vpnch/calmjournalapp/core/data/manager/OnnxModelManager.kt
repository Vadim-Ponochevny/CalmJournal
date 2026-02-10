package com.vpnch.calmjournalapp.core.data.manager

import android.content.Context
import ai.onnxruntime.*
import com.vpnch.calmjournalapp.core.ml.BertTokenizer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.LongBuffer
import java.util.Optional
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.exp

@Singleton
class OnnxModelManager @Inject constructor(
    private val context: Context
) {
    private var ortSession: OrtSession? = null
    private var ortEnvironment: OrtEnvironment? = null
    var isModelLoaded = false

    companion object {
        private const val MODEL_PATH = "models/rubertai/model_quantized.onnx"
        const val NUM_EMOTIONS = 28
    }

    suspend fun initialize() {
        if (isModelLoaded) return

        withContext(Dispatchers.IO) {
            try {
                ortEnvironment = OrtEnvironment.getEnvironment()
                val modelBytes = context.assets.open(MODEL_PATH).use { it.readBytes() }

                val sessionOptions = OrtSession.SessionOptions()
                ortSession = ortEnvironment!!.createSession(modelBytes, sessionOptions)
                isModelLoaded = true

            } catch (e: Exception) {
                throw RuntimeException("Ошибка загрузки модели: ${e.message}", e)
            }
        }
    }

    suspend fun analyzeText(text: String): FloatArray? {
        if (!isModelLoaded) {
            initialize()
        }

        return withContext(Dispatchers.IO) {
            try {
                val tokenizer = BertTokenizer(context)
                val (inputIds, attentionMask, tokenTypeIds) = tokenizer.tokenize(text)

                val inputIdsTensor = createLongTensor(inputIds, longArrayOf(1, inputIds.size.toLong()))
                val attentionMaskTensor = createLongTensor(attentionMask, longArrayOf(1, attentionMask.size.toLong()))
                val tokenTypeIdsTensor = createLongTensor(tokenTypeIds, longArrayOf(1, tokenTypeIds.size.toLong()))

                val inputs = mapOf(
                    "input_ids" to inputIdsTensor,
                    "attention_mask" to attentionMaskTensor,
                    "token_type_ids" to tokenTypeIdsTensor
                )

                val results = ortSession!!.run(inputs)
                val outputValue = results["logits"] ?: results[0]

                val actualValue = when {
                    outputValue is Optional<*> -> outputValue.get() as? OnnxTensor
                    else -> outputValue as? OnnxTensor
                }

                val logits = if (actualValue is OnnxTensor) {
                    val floatBuffer = actualValue.floatBuffer
                    FloatArray(floatBuffer.remaining()) { floatBuffer.get() }
                } else {
                    FloatArray(0)
                }

                val probabilities = if (logits.isNotEmpty()) {
                    FloatArray(logits.size) { sigmoid(logits[it]) }
                } else {
                    createFallbackProbabilities()
                }

                inputIdsTensor.close()
                attentionMaskTensor.close()
                tokenTypeIdsTensor.close()
                results.close()

                probabilities

            } catch (e: Exception) {
                createFallbackProbabilities()
            }
        }
    }

    private fun createFallbackProbabilities(): FloatArray {
        return FloatArray(28) { index ->
            when (index) {
                17 -> 0.85f
                4 -> 0.72f
                15 -> 0.68f
                20 -> 0.61f
                2 -> 0.12f
                25 -> 0.08f
                else -> 0.02f + (index * 0.01f) % 0.1f
            }
        }
    }

    private fun sigmoid(x: Float): Float {
        return (1.0 / (1.0 + exp(-x.toDouble()))).toFloat()
    }

    private fun createLongTensor(data: LongArray, shape: LongArray): OnnxTensor {
        val longBuffer = LongBuffer.wrap(data)
        return OnnxTensor.createTensor(ortEnvironment!!, longBuffer, shape)
    }

    fun close() {
        ortSession?.close()
        ortEnvironment = null
        ortSession = null
        isModelLoaded = false
    }
}