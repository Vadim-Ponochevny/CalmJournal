package com.vpnch.calmjournalapp.core.data.ml.emotion.tensor

import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import java.nio.LongBuffer

class TensorService(
    private val ortEnvironment: OrtEnvironment
) {

    fun createLongTensor(data: LongArray, shape: LongArray): OnnxTensor {
        val longBuffer = LongBuffer.wrap(data)
        return OnnxTensor.createTensor(ortEnvironment, longBuffer, shape)
    }

    fun prepareInputTensors(
        inputIds: LongArray,
        attentionMask: LongArray,
        tokenTypeIds: LongArray
    ): Map<String, OnnxTensor> {
        val batchSize = 1L

        val inputIdsTensor = createLongTensor(
            inputIds,
            longArrayOf(batchSize, inputIds.size.toLong())
        )

        val attentionMaskTensor = createLongTensor(
            attentionMask,
            longArrayOf(batchSize, attentionMask.size.toLong())
        )

        val tokenTypeIdsTensor = createLongTensor(
            tokenTypeIds,
            longArrayOf(batchSize, tokenTypeIds.size.toLong())
        )

        return mapOf(
            "input_ids" to inputIdsTensor,
            "attention_mask" to attentionMaskTensor,
            "token_type_ids" to tokenTypeIdsTensor
        )
    }
}