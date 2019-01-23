package com.tegnatiek.omw.wordio.loader


import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader
import com.tegnatiek.omw.wordio.loader.text.TextFileHandler
import com.tegnatiek.omw.wordio.loader.text.TextLoader
import java.util.*

class AssetLoader : IAssetLoader {
    companion object {
        private const val PATH_DATA_FONT = "data/roboto.ttf"
        private const val PATH_DATA_WORD_LST = "data/word.list"
    }

    private var manager: AssetManager = AssetManager()
    private val wordsList: ArrayList<String> = ArrayList()

    init {
        initFontLoader()
        initWordListLoader()
        manager.finishLoading()
    }

    override fun getFont(): BitmapFont {
        return manager.get(PATH_DATA_FONT, BitmapFont::class.java)
    }

    override fun getWordList(): ArrayList<String> {
        if (wordsList.isNotEmpty())
            return wordsList
        val tmpArray = manager.get(PATH_DATA_WORD_LST, TextFileHandler::class.java)
        val words = tmpArray.string!!.split('\n')
        wordsList.addAll(words)
        return wordsList
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
