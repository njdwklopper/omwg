package com.tegnatiek.omw.wordio

import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.util.*

class WordLoader @Throws(IOException::class)
constructor(wordFileReader: InputStream) {

    private val wordList = ArrayList<String>(120000)

    init {
        readWordsIntoArray(wordFileReader)
    }

    private fun readWordsIntoArray(stream: InputStream) {
        val `in` = BufferedInputStream(stream)
        try {
            val buffer = ByteArray(1024 * 20)
            val word = IntArray(100)
            var charIdx = 0
            var readBytes: Int

            while (`in`.read(buffer).let { readBytes = it; it != -1 }) {
                charIdx = readBuffer(buffer, readBytes, word, charIdx)
            }
        } catch (ignored: Exception) {
        } finally {
            try {
                `in`.close()
            } catch (ignored: Exception) {
            }
        }
    }

    private fun readBuffer(buffer: ByteArray, readBytes: Int, word: IntArray, charIdx: Int): Int {
        var charIdx = charIdx
        for (i in 0 until readBytes) {
            val c = buffer[i]
            if (c == '\n'.toByte()) {
                val wordStr = String(word, 0, charIdx)
                wordList.add(wordStr)
                charIdx = 0
            } else {
                word[charIdx++] = c.toInt()
            }
        }
        return charIdx
    }

    fun getWordList(): List<String> {
        return wordList
    }
}