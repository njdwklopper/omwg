package com.tegnatiek.omw.wordengine

import com.tegnatiek.omw.wordio.loader.IAssetLoader
import java.util.*
import java.util.regex.Pattern

class WordHelper(private val wordLoader: IAssetLoader) {

    companion object {
        const val MAX_COLUMNS = 4
        const val MAX_ROWS = 14
        const val MAX_ALL = MAX_COLUMNS * MAX_ROWS
        const val MIN_ALL = MAX_ALL / 3
    }

    fun getRandomSixLetterWordFromList(): String {
        val tmpList = getTemporaryWordListByWordLength(6)
        return tmpList[getRandomIntByMax(tmpList.size)]
    }

    fun countTotalWordsGenerated(gameBoard: List<List<String>>): Int {
        var counter = 0
        for (board in gameBoard) {
            for (ignored in board) {
                counter++
            }
        }
        return counter
    }

    fun getWordsGameBoard(chosenWord: String): ArrayList<ArrayList<String>> {
        val gameBoard = ArrayList<ArrayList<String>>()
        createGameBoard(chosenWord, gameBoard)
        return if (countTotalWordsGenerated(gameBoard) in MIN_ALL..MAX_ALL) {
            gameBoard
        } else {
            getWordsGameBoard(getRandomSixLetterWordFromList())
        }
    }

    private fun createGameBoard(chosenWord: String, gameBoard: ArrayList<ArrayList<String>>) {
        var tmpList: ArrayList<String>
        for (i in 3..5) {
            tmpList = getWordsMatchingStringCharSet(chosenWord, i)
            gameBoard.add(tmpList)
        }
    }

    fun doesWordContainThisManyChars(word: String, length: Int): Boolean {
        return word.length == length
    }

    fun getWordsMatchingStringCharSet(string: String, wordSize: Int): ArrayList<String> {
        val tmpList = getTemporaryWordListByWordLength(wordSize)
        val newList = ArrayList<String>()

        for (word in tmpList) {
            var matchTotal = 0
            var wordUsed = string
            val wordCharArray = word.toCharArray()
            for (wordChar in wordCharArray) {
                if (doesCharacterMatchInsideWords(wordChar, wordUsed)) {
                    wordUsed = removeCharacter(wordUsed, wordChar)
                    matchTotal++
                }
                if (matchTotal == wordSize) {
                    newList.add(word)
                    matchTotal = 0
                }
            }
        }
        return newList
    }

    fun shuffleString(text: String): String {
        val characters = text.toCharArray()
        for (i in characters.indices) {
            val randomIndex = getRandomIntByMax(characters.size - 1)
            val temp = characters[i]
            characters[i] = characters[randomIndex]
            characters[randomIndex] = temp
        }
        return isShuffledStringAccidentallyTheWord(text, String(characters))
    }

    fun isShuffledStringAccidentallyTheWord(word: String, shuffled: String): String {
        return if (word == shuffled) {
            shuffleString(word)
        } else shuffled
    }

    fun getTemporaryWordListByWordLength(length: Int): List<String> {
        val tmpList = ArrayList<String>(length)
        val wordList = wordLoader.getWordList()
        for (word in wordList) {
            if (doesWordContainThisManyChars(word, length))
                tmpList.add(word)
        }
        return tmpList
    }

    fun doesCharacterMatchInsideWords(wordChar: Char, word: String): Boolean {
        val pattern = Pattern.compile(".*[$wordChar].*")
        val matcher = pattern.matcher(word)
        return matcher.find()
    }

    fun removeCharacter(string: String, ch: Char): String {
        return string.substring(0, string.indexOf(ch)) + string.substring(string.indexOf(ch) + 1)
    }

    private fun getRandomIntByMax(max: Int): Int {
        return (0..max).random()
    }
}