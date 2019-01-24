package com.tegnatiek.omw.wordengine.model

import com.badlogic.gdx.utils.ObjectMap

class Board {
    lateinit var gameWordAnswerChosen: String
    lateinit var gameWordPlayableTiles: Word
    //    private val gameWordPlayedTiles: Word = null!!
//    private val gameWordBonus: Word
    lateinit var scoreBoard: ScoreBoard

    private var gameBoard: ObjectMap<String, Word> = ObjectMap()

    fun getGameBoard(): ObjectMap<String, Word> {
        return gameBoard
    }

    fun setGameBoard(gameBoard: ObjectMap<String, Word>) {
        this.gameBoard = gameBoard
    }

    fun dispose() {
        gameBoard.clear()
    }
}