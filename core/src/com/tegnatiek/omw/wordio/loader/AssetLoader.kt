package com.tegnatiek.omw.wordio.loader

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.tegnatiek.omw.wordio.loader.text.TextFileHandler
import com.tegnatiek.omw.wordio.loader.text.TextLoader
import java.util.*

class AssetLoader : IAssetLoader {
    companion object {
        private const val PATH_DATA_WORD_LST = "data/word.list"
    }

    private var manager: AssetManager = AssetManager()
    private val wordsList: ArrayList<String> = ArrayList()

    init {
        initWordListLoader()
        manager.finishLoading()
    }

    override fun getWordList(): ArrayList<String> {
        if (wordsList.isNotEmpty())
            return wordsList
        val tmpArray = manager.get(PATH_DATA_WORD_LST, TextFileHandler::class.java)
        val words = tmpArray.string!!.split('\n')
        wordsList.addAll(words)
        return wordsList
    }

    private fun initWordListLoader() {
        manager.setLoader(
                TextFileHandler::class.java,
                TextLoader(InternalFileHandleResolver())
        )
        manager.load(
                AssetDescriptor<TextFileHandler>(
                        PATH_DATA_WORD_LST,
                        TextFileHandler::class.java,
                        TextLoader.TextParameter()
                )
        )
    }

    override fun update(): Boolean {
        return manager.update()
    }

    override fun destroy() {
        wordsList.clear()
        manager.dispose()
    }
}
