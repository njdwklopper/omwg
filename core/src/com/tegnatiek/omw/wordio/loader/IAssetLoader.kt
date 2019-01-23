package com.tegnatiek.omw.wordio.loader

import com.badlogic.gdx.graphics.g2d.BitmapFont
import java.util.*

interface IAssetLoader {
    fun getWordList(): ArrayList<String>

    val font: BitmapFont?
    fun update(): Boolean

    fun destroy()
}
