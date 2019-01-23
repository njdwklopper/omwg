package com.tegnatiek.omw.wordio.loader.text

import com.badlogic.gdx.files.FileHandle

class TextFileHandler {

    var string: String? = null

    constructor() {
        this.string = String("".toByteArray())
    }

    constructor(data: ByteArray) {
        this.string = String(data)
    }

    constructor(string: String) {
        this.string = String(string.toByteArray())
    }

    constructor(file: FileHandle) {
        this.string = String(file.readBytes())
    }

    constructor(text: TextFileHandler) {
        this.string = String(text.string!!.toByteArray())
    }

    fun clear() {
        this.string = String("".toByteArray())
    }
}