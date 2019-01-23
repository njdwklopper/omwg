package com.tegnatiek.omw.wordio


import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.tegnatiek.omw.wordio.loader.IAssetLoader
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.nio.charset.Charset
import java.util.*

class WordLoaderStub : IAssetLoader {

    companion object {
        //To run test with this class, run test with android/assets on path in config
        private const val WORDLIST_PATH = "data/word.list"
    }

    private var wordLists: ArrayList<String> = ArrayList(120000)

    override fun getWordList(): ArrayList<String> {
        return wordLists
    }

    override fun getFont(): BitmapFont {return BitmapFont()}

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
        wordLists = content.split('\n') as ArrayList<String>
    }

    override fun destroy() {
        wordLists.clear()
    }

    override fun update(): Boolean {
        return false
    }
}