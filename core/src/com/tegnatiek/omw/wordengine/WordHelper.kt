package com.tegnatiek.omw.wordengine

import com.tegnatiek.omw.wordio.loader.IAssetLoader
import java.util.*
import java.util.regex.Pattern

class WordHelper(private val wordLoader: IAssetLoader) {

    val randomSixLetterWordFromList: String
        get() {
            val tmpList = getTemporaryWordListbyWordLength(6)
            return tmpList[getRandomIntByMax(tmpList.size - 1)]
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

    fun getWordsGameBoard(chosenWord: String): List<List<String>> {
        val gameBoard = ArrayList<List<String>>()
        createGameBoard(chosenWord, gameBoard)

        val count = countTotalWordsGenerated(gameBoard)
        return if (count >= MIN_ALL && count <= MAX_ALL) {
            gameBoard
        } else {
            getWordsGameBoard(randomSixLetterWordFromList)
        }
    }

    private fun createGameBoard(chosenWord: String, gameBoard: MutableList<List<String>>) {
        var tmpList: List<String>
        for (i in 3..5) {
            tmpList = getWordsMatchingStringCharSet(chosenWord, i)
            gameBoard.add(tmpList)
        }
    }

    fun doesWordContainThisManyChars(word: String, length: Int): Boolean {
        return word.length == length
    }

    fun getWordsMatchingStringCharSet(string: String, wordSize: Int): List<String> {
        val tmpList = getTemporaryWordListbyWordLength(wordSize)
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

    fun getTemporaryWordListbyWordLength(length: Int): List<String> {
        val tmpList = ArrayList<String>(length)
        val wordList = wordLoader.wordList
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

    fun getRandomIntByMax(max: Int): Int {
        val tmp = (0..max).random()
        return tmp
    }

    companion object {

        val MAX_COLUMNS = 4
        val MAX_ROWS = 14
        val MAX_ALL = MAX_COLUMNS * MAX_ROWS
        val MIN_ALL = MAX_ALL / 3
    }
}