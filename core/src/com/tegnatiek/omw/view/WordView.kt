package com.tegnatiek.omw.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.tegnatiek.omw.wordengine.model.Letter
import com.tegnatiek.omw.wordengine.model.Word
import com.tegnatiek.omw.wordio.letters.LetterSprites
import java.util.*

class WordView(
        model: Word,
        private val letterSprite: LetterSprites) {

    private val sprites: ArrayList<Sprite> = ArrayList()

    init {
        model.height = letterSprite.get('x').height
        model.width = 0f

        for (letter: Letter in model.wordLetters) {
            val sprite = letterSprite.get(letter.letter)
            sprite.setPosition(model.pos.x + model.width, model.pos.y)
            sprite.color = Color(1f, 1f, 0.9f, 1f)
            model.width += sprite.width
            sprites.add(sprite)
        }
    }

    fun draw(batch: SpriteBatch) {
        for (sprite: Sprite in sprites) {
            sprite.draw(batch)
        }
    }

    fun dispose() {
        letterSprite.dispose()
    }
}