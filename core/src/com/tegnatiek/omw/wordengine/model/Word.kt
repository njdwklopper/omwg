package com.tegnatiek.omw.wordengine.model

import com.badlogic.gdx.math.Vector2
import java.util.ArrayList

class Word {
    var wordLetters: ArrayList<Letter> = ArrayList()
    var combinedLetterScore: Int = 0

    var pos: Vector2 = Vector2(0f, 0f)
    var width: Float = 0.0f
    var height: Float = 0.0f
}
