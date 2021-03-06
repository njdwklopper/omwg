package com.tegnatiek.omw.wordio.loader

import com.badlogic.gdx.graphics.g2d.BitmapFont
import java.util.*

interface IAssetLoader {
    fun getWordList(): ArrayList<String>
    fun getFont(): BitmapFont
    fun update(): Boolean
    fun destroy()
}
