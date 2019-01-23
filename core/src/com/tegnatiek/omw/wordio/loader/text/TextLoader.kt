package com.tegnatiek.omw.wordio.loader.text

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetLoaderParameters
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.Array

class TextLoader(resolver: FileHandleResolver) : AsynchronousAssetLoader<TextFileHandler, TextLoader.TextParameter>(resolver) {

    private var text: TextFileHandler? = null

    override fun loadAsync(
            manager: AssetManager,
            fileName: String,
            file: FileHandle,
            parameter: TextParameter
    ) {
        this.text = null
        this.text = TextFileHandler(file)
    }

    override fun loadSync(
            manager: AssetManager,
            fileName: String,
            file: FileHandle,
            parameter: TextParameter
    ): TextFileHandler? {
        val text = this.text
        this.text = null
        return text
    }

    override fun getDependencies(
            fileName: String,
            file: FileHandle,
            parameter: TextParameter
    ): Array<AssetDescriptor<*>>? {
        return null
    }

    class TextParameter : AssetLoaderParameters<TextFileHandler>()
}