package com.vpnch.calmjournalapp.data.emotions.ml.tokenizer

import android.content.Context
import android.util.Log
import java.io.BufferedReader

class BertTokenizer(context: Context) {

    companion object {
        private const val UNK_TOKEN = "[UNK]"
        private const val CLS_TOKEN = "[CLS]"
        private const val SEP_TOKEN = "[SEP]"
        private const val PAD_TOKEN = "[PAD]"
        private const val MAX_LENGTH = 1024
        private const val MAX_WORD_LENGTH = 100
        private const val TOKEN_CACHE_SIZE = 5000
    }

    private val vocab = mutableMapOf<String, Int>()
    private val idToToken = mutableMapOf<Int, String>()
    private val trie = TokenTrie()
    private val tokenCache = LinkedHashMap<String, List<String>>(TOKEN_CACHE_SIZE, 0.75f, true)

    init {
        loadVocabulary(context)
        buildTrie()
    }

    private fun loadVocabulary(context: Context) {
        try {
            context.assets.open("models/rubertai/vocab.txt").use { inputStream ->
                val reader = BufferedReader(inputStream.reader())

                var index = 0
                reader.useLines { lines ->
                    lines.forEach { token ->
                        vocab[token] = index
                        idToToken[index] = token
                        index++
                    }
                }
            }
            Log.i("BertTokenizer", "Vocabulary loaded: ${vocab.size} tokens")
        } catch (e: Exception) {
            throw RuntimeException("Failed to load vocabulary", e)
        }
    }

    private fun buildTrie() {
        vocab.keys.forEach { token ->
            val cleanToken = token.replace("##", "")
            trie.insert(cleanToken, token)
        }
        Log.i("BertTokenizer", "Prefix tree built")
    }

    fun tokenize(text: String, maxLength: Int = MAX_LENGTH): TokenizationResult {
        val cleanedText = text.lowercase()
            .replace("\n", " ")
            .replace("\r", " ")
            .trim()

        val tokens = mutableListOf<String>().apply {
            add(CLS_TOKEN)
            addAll(tokenizeText(cleanedText))
            add(SEP_TOKEN)
        }

        val processedTokens = truncateTokens(tokens, maxLength)
        return createTokenizationResult(processedTokens, maxLength)
    }

    private fun tokenizeText(text: String): List<String> {
        return text.split(" ").flatMap { word ->
            when {
                word.isEmpty() -> emptyList()
                word in vocab -> listOf(word)
                else -> wordpieceTokenize(word)
            }
        }
    }

    private fun truncateTokens(tokens: List<String>, maxLength: Int): List<String> {
        return if (tokens.size > maxLength) {
            tokens.take(maxLength - 1) + SEP_TOKEN
        } else {
            tokens
        }
    }

    private fun createTokenizationResult(tokens: List<String>, maxLength: Int): TokenizationResult {
        val inputIds = tokens.map { tokenToId(it) }.toLongArray()
        val attentionMask = LongArray(inputIds.size) { 1L }
        val tokenTypeIds = LongArray(inputIds.size) { 0L }

        return if (inputIds.size < maxLength) {
            val paddedInputIds = padArray(inputIds, maxLength, vocab[PAD_TOKEN]?.toLong() ?: 0L)
            val paddedAttentionMask = padArray(attentionMask, maxLength, 0L)
            val paddedTokenTypeIds = padArray(tokenTypeIds, maxLength, 0L)
            TokenizationResult(paddedInputIds, paddedAttentionMask, paddedTokenTypeIds)
        } else {
            TokenizationResult(inputIds, attentionMask, tokenTypeIds)
        }
    }

    private fun tokenToId(token: String): Long {
        return vocab[token]?.toLong() ?: vocab[UNK_TOKEN]?.toLong() ?: 100L
    }

    private fun <T> padArray(array: LongArray, targetSize: Int, padValue: T): LongArray
            where T : Number {
        return if (array.size < targetSize) {
            array + LongArray(targetSize - array.size) { padValue.toLong() }
        } else {
            array
        }
    }

    private fun wordpieceTokenize(word: String): List<String> {
        return tokenCache.getOrPut(word) {
            when {
                word.length > MAX_WORD_LENGTH -> tokenizeLongWord(word)
                "##$word" in vocab -> listOf("##$word")
                else -> tokenizeWithTrie(word)
            }
        }
    }

    private fun tokenizeWithTrie(word: String): List<String> {
        val tokens = mutableListOf<String>()
        var position = 0

        while (position < word.length) {
            trie.findLongestMatch(word, position)?.let { (token, length) ->
                val finalToken = if (position == 0) token else "##$token"
                tokens.add(finalToken)
                position += length
            } ?: run {
                val (newTokens, newPosition) = handleUnknownCharacters(word, position)
                tokens.addAll(newTokens)
                position = newPosition
            }
        }

        return if (tokens.isEmpty()) listOf(UNK_TOKEN) else tokens
    }

    private fun handleUnknownCharacters(word: String, startPos: Int): Pair<List<String>, Int> {
        val tokens = mutableListOf<String>()
        var position = startPos

        while (position < word.length) {
            val charToken = if (position == 0) word[position].toString() else "##${word[position]}"

            if (charToken in vocab) {
                tokens.add(charToken)
                position++
            } else {
                tokens.add(UNK_TOKEN)
                position++
                break
            }
        }

        return tokens to position
    }

    private fun tokenizeLongWord(word: String): List<String> {
        val tokens = mutableListOf<String>()
        val maxChunkSize = MAX_WORD_LENGTH / 2
        var start = 0

        while (start < word.length) {
            val chunkSize = minOf(maxChunkSize, word.length - start)
            val chunk = word.substring(start, start + chunkSize)

            val chunkTokens = if (chunk in vocab) {
                listOf(if (start == 0) chunk else "##$chunk")
            } else {
                tokenizeWithTrie(chunk).map { token ->
                    if (start > 0 && !token.startsWith("##")) "##$token" else token
                }
            }

            tokens.addAll(chunkTokens)
            start += chunkSize
        }

        return if (tokens.isEmpty()) listOf(UNK_TOKEN) else tokens
    }

    fun decode(ids: LongArray): String {
        return ids
            .asIterable()
            .mapNotNull { id ->
                idToToken[id.toInt()]?.replace("##", "")
            }
            .joinToString(" ")
            .trim()
    }

    fun clearCache() {
        tokenCache.clear()
    }

    data class TokenizationResult(
        val inputIds: LongArray,
        val attentionMask: LongArray,
        val tokenTypeIds: LongArray
    )

    private class TokenTrie {
        private data class Node(
            var token: String? = null,
            var isEnd: Boolean = false,
            val children: MutableMap<Char, Node> = mutableMapOf()
        )

        private val root = Node()

        fun insert(word: String, originalToken: String) {
            var current = root
            word.forEach { char ->
                current = current.children.getOrPut(char) { Node() }
            }
            current.isEnd = true
            current.token = originalToken
        }

        fun findLongestMatch(text: String, startPos: Int): Pair<String, Int>? {
            var current = root
            var longestMatch: Pair<String, Int>? = null
            var position = startPos

            while (position < text.length) {
                val char = text[position]
                current = current.children[char] ?: break

                position++
                if (current.isEnd) {
                    val matchedToken = current.token ?: text.substring(startPos, position)
                    longestMatch = matchedToken to (position - startPos)
                }
            }

            return longestMatch
        }
    }
}