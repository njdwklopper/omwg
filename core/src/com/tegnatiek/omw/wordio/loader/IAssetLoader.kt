package com.tegnatiek.omw.wordio.loader

import com.badlogic.gdx.graphics.g2d.BitmapFont
import java.util.*

interface IAssetLoader {
    val wordList: ArrayList<String>

    val font: BitmapFont?
    fun update(): Boolean

    fun destroy()
}
