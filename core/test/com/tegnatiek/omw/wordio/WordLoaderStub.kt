package com.tegnatiek.omw.wordio


import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.tegnatiek.omw.wordio.loader.IAssetLoader
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.nio.charset.Charset
import java.util.*

class WordLoaderStub() : IAssetLoader {
    companion object {
        //To run test with this class, run test with android/assets on path
        private const val WORDLIST_PATH = "word.list"
    }

    override var wordList: ArrayList<String> = ArrayList(120000)
        private set

    override val font: BitmapFont? = null

    init {
        try {
            val inputStream = FileInputStream(WORDLIST_PATH)
            readIntoWords(inputStream)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun readIntoWords(inputStream: FileInputStream) {
        val content = inputStream.readBytes().toString(Charset.defaultCharset())
        wordList = content.split('\n') as ArrayList<String>
    }

    override fun destroy() {
        wordList.clear()
    }

    override fun update(): Boolean {
        return false
    }
}