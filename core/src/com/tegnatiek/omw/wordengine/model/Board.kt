package com.tegnatiek.omw.wordengine.model

import com.badlogic.gdx.utils.ObjectMap

class Board {
    var gameWordAnswerChosen: String? = null
    var gameWordPlayableTiles: Word? = null
    private val gameWordPlayedTiles: Word? = null
    private val gameWordBonus: Word? = null
    var scoreBoard: ScoreBoard? = null
    private var gameBoard: ObjectMap<String, Word>? = ObjectMap()

    fun getGameBoard(): ObjectMap<String, Word>? {
        return gameBoard
    }

    fun setGameBoard(gameBoard: ObjectMap<String, Word>) {
        this.gameBoard = gameBoard
    }

    fun destroy() {
        gameBoard!!.clear()
        gameBoard = null
    }
}