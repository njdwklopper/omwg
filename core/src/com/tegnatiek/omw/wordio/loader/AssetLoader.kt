package com.tegnatiek.omw.wordio.loader

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader
import com.tegnatiek.omw.wordio.loader.text.TextLoader
import java.util.*
import com.tegnatiek.omw.wordio.loader.text.TextFileHandler


class AssetLoader : IAssetLoader {
    private var manager: AssetManager = AssetManager()

    override val font: BitmapFont
        get() = manager.get(PATH_DATA_FONT, BitmapFont::class.java)

    private val wordsList: ArrayList<String> = ArrayList()

    override fun getWordList(): ArrayList<String> {
        if (wordsList.isNotEmpty())
            return wordsList
        val tmpArray = manager.get(PATH_DATA_WORD_LST, TextFileHandler::class.java)
        val words = tmpArray.string!!.split('\n')
        println("Lettersize: " + words.size)
        wordsList.addAll(words)
        return wordsList
    }

    init {
        initFontLoader()
        initWordListLoader()
        manager.finishLoading()
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

    private fun initFontLoader() {
        val resolver = InternalFileHandleResolver()
        manager.setLoader<FreeTypeFontGenerator, FreeTypeFontGeneratorLoader.FreeTypeFontGeneratorParameters>(FreeTypeFontGenerator::class.java, FreeTypeFontGeneratorLoader(resolver))
        manager.setLoader<BitmapFont, FreetypeFontLoader.FreeTypeFontLoaderParameter>(BitmapFont::class.java, PATH_DATA_FONT, FreetypeFontLoader(resolver))

        val parameter = FreetypeFontLoader.FreeTypeFontLoaderParameter()
        parameter.fontParameters.size = 100
        parameter.fontParameters.color = Color.WHITE
        parameter.fontFileName = PATH_DATA_FONT
        manager.load(PATH_DATA_FONT, BitmapFont::class.java, parameter)
    }

    override fun destroy() {
        wordsList.clear()
        manager.dispose()
    }

    companion object {
        private val PATH_DATA_FONT = "data/roboto.ttf"
        private val PATH_DATA_WORD_LST = "data/word.list"
    }
}
