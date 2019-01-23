package com.tegnatiek.omw

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.utils.ObjectMap
import com.tegnatiek.omw.wordengine.WordHelper
import com.tegnatiek.omw.wordengine.WordScoreMap
import com.tegnatiek.omw.wordengine.model.Board
import com.tegnatiek.omw.wordengine.model.Letter
import com.tegnatiek.omw.wordengine.model.ScoreBoard
import com.tegnatiek.omw.wordengine.model.Word
import com.tegnatiek.omw.wordio.loader.AssetLoader
import com.tegnatiek.omw.wordio.loader.IAssetLoader
import java.util.*

class GamePlayController {

    var gameState = STATE_GAME_START
        private set

    var boardModel: Board = Board()
    private var assetLoader: IAssetLoader = AssetLoader()
    private var wordHelper: WordHelper = WordHelper(AssetLoader())
    private var wordScoreMap: WordScoreMap = WordScoreMap()

    val font: BitmapFont?
        get() = assetLoader.font

    private fun getGameboardWordList(): ArrayList<ArrayList<String>> {
        return wordHelper.getWordsGameBoard(
                boardModel.gameWordAnswerChosen!!
        )
    }

    fun changeStateAfterLoadingImageComplete() {
        if (assetLoader.update()) {
            gameState = STATE_GAME_LOADING_OTHER_ASSETS
        }
    }

    fun changeStateToGamePlay() {
        if (assetLoader.getWordList().isNotEmpty()) {
            gameState = STATE_GAME_PLAY
        }
    }

    fun refreshBoard() {
        initNewGameBoard()
    }

    private fun initNewGameWord() {
        boardModel.gameWordAnswerChosen = wordHelper.getRandomSixLetterWordFromList()
        boardModel.gameWordPlayableTiles = getNewWordModel(
                wordHelper.shuffleString(
                        boardModel.gameWordAnswerChosen!!
                )
        )
    }

    private fun initNewGameBoard() {
        initNewGameWord()
        initNewScoreBoard()
        val gameBoard = getGameboardWordList()
        val gameBoardModel = ObjectMap<String, Word>(
                boardModel.scoreBoard!!.totalWordsAvailable
        )
        for (board in gameBoard) {
            for (word in board) {
                gameBoardModel.put(
                        word,
                        getNewWordModel(word)
                )
            }
        }
        boardModel.setGameBoard(gameBoardModel)
    }

    private fun getNewWordModel(wordString: String): Word {
        val word = Word()
        val characters = wordString.toCharArray()
        val letters = ArrayList<Letter>(characters.size)
        for (ch in characters) {
            letters.add(getNewLetterModel(ch))
        }
        word.wordLetters = letters
        word.combinedLetterScore = getCombinedCalculatedLetterScore(letters)
        //TODO setup coordinate system
        //word.setCoordinates();
        return word
    }

    private fun getCombinedCalculatedLetterScore(letters: ArrayList<Letter>): Int {
        var combinedScore = 0
        for (letter in letters)
            combinedScore += letter.scoreValue
        return combinedScore
    }

    private fun getNewLetterModel(letterChar: Char): Letter {
        val letter = Letter()
        letter.letter = letterChar
        letter.scoreValue = wordScoreMap.getScoreForLetter(
                letter.letter
        )
        return letter
    }

    private fun initNewScoreBoard() {
        val scoreBoard = ScoreBoard()
        val totalWordsAvailable = wordHelper.countTotalWordsGenerated(
                getGameboardWordList()
        )
        scoreBoard.totalWordsAvailable = totalWordsAvailable
        scoreBoard.totalWordsNotFound = totalWordsAvailable
        scoreBoard.totalWordsToGo = totalWordsAvailable
        boardModel.scoreBoard = scoreBoard
    }

    fun destroy() {
        boardModel.destroy()
        assetLoader.destroy()
    }

    companion object {

        val STATE_GAME_START = 0
        val STATE_GAME_LOADING_OTHER_ASSETS = 1
        val STATE_GAME_PLAY = 2
    }
}