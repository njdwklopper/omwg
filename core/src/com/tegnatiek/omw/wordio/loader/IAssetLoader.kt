package com.tegnatiek.omw.wordio.loader

import java.util.*

interface IAssetLoader {
    fun getWordList(): ArrayList<String>
    fun update(): Boolean
    fun destroy()
}
