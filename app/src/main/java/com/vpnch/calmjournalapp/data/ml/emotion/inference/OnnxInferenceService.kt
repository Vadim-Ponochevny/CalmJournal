package com.vpnch.calmjournalapp.data.ml.emotion.inference

import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtSession
import java.util.Optional

class OnnxInferenceService(private val ortSession: OrtSession) {

    suspend fun runInference(inputs: Map<String, OnnxTensor>): FloatArray {
        val results = ortSession.run(inputs)
        val outputValue = results["logits"] ?: results[0]

        val actualValue = when {
            outputValue is Optional<*> -> outputValue.get() as? OnnxTensor
            else -> outputValue as? OnnxTensor
        }

        return if (actualValue is OnnxTensor) {
            val floatBuffer = actualValue.floatBuffer
            FloatArray(floatBuffer.remaining()) { floatBuffer.get() }
        } else {
            FloatArray(0)
        }.also {
            results.close()
            inputs.values.forEach { it.close() }
        }
    }
}