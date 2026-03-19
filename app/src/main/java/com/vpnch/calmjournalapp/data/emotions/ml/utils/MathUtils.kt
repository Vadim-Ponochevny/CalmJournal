package com.vpnch.calmjournalapp.data.emotions.ml.utils

import kotlin.math.exp

object MathUtils {

    fun sigmoid(x: Float): Float {
        return (1.0 / (1.0 + exp(-x.toDouble()))).toFloat()
    }


    fun applySigmoidToArray(logits: FloatArray): FloatArray {
        return FloatArray(logits.size) { sigmoid(logits[it]) }
    }
}