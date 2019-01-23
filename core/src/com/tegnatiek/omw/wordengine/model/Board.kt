package com.tegnatiek.omw.wordengine.model

import java.util.LinkedHashMap

class Board {
    var gameWordAnswerChosen: String? = null
    var gameWordPlayableTiles: Word? = null
    private val gameWordPlayedTiles: Word? = null
    private val gameWordBonus: Word? = null
    var scoreBoard: ScoreBoard? = null
    private var gameBoard: MutableMap<String, Word>? = LinkedHashMap<String, Word>()

    fun getGameBoard(): Map<String, Word>? {
        return gameBoard
    }

    fun setGameBoard(gameBoard: MutableMap<String, Word>) {
        this.gameBoard = gameBoard
    }

    fun destroy() {
        gameBoard!!.clear()
        gameBoard = null
    }
}