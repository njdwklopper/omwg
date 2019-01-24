package com.tegnatiek.omw.wordio.letters

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.ObjectMap


class LetterSprites {
    companion object {
        private const val PATH_SPRITE_ATLAS_LETTERS = "sprites/letters.txt"
        private const val CHARS = "abcdefghijklmnopqrstuvwxyz"
    }

    private var textureAtlas: TextureAtlas = TextureAtlas(PATH_SPRITE_ATLAS_LETTERS)
    private var letters: ObjectMap<Char, Sprite> = ObjectMap()

    init {
        loadSprites()
    }

    private fun loadSprites() {
        letters.clear()
        for (i in 0..CHARS.length) {
            letters.put(CHARS[i], textureAtlas.createSprite("letter_A"))
        }
    }

    fun get(char: Char): Sprite {
        return letters.get(char.toLowerCase(), textureAtlas.createSprite("underscore"))
    }

    fun dispose() {
        letters.clear()
        textureAtlas.dispose()
    }
}