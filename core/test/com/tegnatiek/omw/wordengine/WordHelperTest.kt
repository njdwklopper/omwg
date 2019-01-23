package com.tegnatiek.omw.wordengine

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.tegnatiek.omw.wordio.WordLoaderStub
import com.tegnatiek.omw.wordio.loader.IAssetLoader
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WordHelperTest {

    private lateinit var assetLoader: IAssetLoader
    private lateinit var wordHelper: WordHelper
    private lateinit var scoreMap: WordScoreMap

    @BeforeEach
    @Throws(Exception::class)
    fun before() {
        assetLoader = WordLoaderStub()
        wordHelper = WordHelper(assetLoader)
        scoreMap = WordScoreMap()
    }

    @AfterEach
    @Throws(Exception::class)
    fun after() {
        assetLoader!!.destroy()
//        assertNull(assetLoader!!.wordList)
    }

    @Test
    @Throws(Exception::class)
    fun testGameBoardCreation() {
        val chosenWord = "limpsy"

        val gameBoard = wordHelper.getWordsGameBoard(chosenWord)
        //Max words
        assertEquals(56, WordHelper.MAX_ALL)

        //Total words generated from chosen word
        assertEquals(29, wordHelper.countTotalWordsGenerated(gameBoard))

        assertTrue(gameBoard[0].contains("imp"))
        assertTrue(gameBoard[0].contains("lip"))
        assertTrue(gameBoard[0].contains("psi"))
        assertTrue(gameBoard[0].contains("yip"))


        assertTrue(gameBoard[1].contains("imps"))
        assertTrue(gameBoard[1].contains("limp"))
        assertTrue(gameBoard[1].contains("slim"))
        assertTrue(gameBoard[1].contains("yips"))

        assertTrue(gameBoard[2].contains("imply"))
        assertTrue(gameBoard[2].contains("limps"))

        println("\nGameBoard for '" + chosenWord + "' Shuffled: " + wordHelper.shuffleString(chosenWord))
    }

    @Test
    @Throws(Exception::class)
    fun testGameBoardCreationNotEnoughWords() {
        val chosenWord = "squirm"

        val gameBoard = wordHelper.getWordsGameBoard(chosenWord)
        //Max words
        assertEquals(56, WordHelper.MAX_ALL)

        //Total words generated from chosen word
        assertTrue(wordHelper.countTotalWordsGenerated(gameBoard) >= WordHelper.MIN_ALL)
        assertTrue(wordHelper.countTotalWordsGenerated(gameBoard) <= WordHelper.MAX_ALL)

        println("\nGameBoard for '" + chosenWord + "' Shuffled: " + wordHelper.shuffleString(chosenWord))
    }

    @Test
    @Throws(Exception::class)
    fun testGetRandomWordFromList() {
        val wordLoaderForThisTest = MockedWordLoader()
        val wordHelperForThisTest = WordHelper(wordLoaderForThisTest)
        assertEquals("tester", wordHelperForThisTest.getRandomSixLetterWordFromList())
    }

    private inner class MockedWordLoader : IAssetLoader {
        override val font: BitmapFont?
            get() = null

        override fun update(): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun destroy() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getWordList(): java.util.ArrayList<String> {
            val wordListTest = ArrayList<String>(0)
            wordListTest.add("tester")
            return wordListTest
        }
    }

    @Test
    @Throws(Exception::class)
    fun testShuffleString() {
        assertNotSame("test", wordHelper.shuffleString("test"))
    }

    @Test
    @Throws(Exception::class)
    fun testShuffleStringIsTheSame() {
        assertNotSame("test", wordHelper.isShuffledStringAccidentallyTheWord("test", "test"))
        assertEquals("tester", wordHelper.isShuffledStringAccidentallyTheWord("test", "tester"))
    }

    @Test
    @Throws(Exception::class)
    fun testDoesWordContainThisManyChars() {
        assertTrue(wordHelper.doesWordContainThisManyChars("test", 4))
        assertTrue(wordHelper.doesWordContainThisManyChars("special", 7))
        assertTrue(wordHelper.doesWordContainThisManyChars("too", 3))
        assertFalse(wordHelper.doesWordContainThisManyChars("test", 3))
        assertFalse(wordHelper.doesWordContainThisManyChars("special", 4))
        assertFalse(wordHelper.doesWordContainThisManyChars("too", 0))
    }

    @Test
    @Throws(Exception::class)
    fun testDoesCharacterMatchInsideWords() {
        assertTrue(wordHelper.doesCharacterMatchInsideWords('c', "chow"))
        assertTrue(wordHelper.doesCharacterMatchInsideWords('h', "chow"))
        assertTrue(wordHelper.doesCharacterMatchInsideWords('o', "chow"))
        assertTrue(wordHelper.doesCharacterMatchInsideWords('w', "chow"))
        assertFalse(wordHelper.doesCharacterMatchInsideWords('z', "chow"))
        assertFalse(wordHelper.doesCharacterMatchInsideWords('t', "chow"))
        assertFalse(wordHelper.doesCharacterMatchInsideWords('p', "chow"))
    }

    @Test
    @Throws(Exception::class)
    fun testGetTemporaryWordListbySize() {
        var tmpList = wordHelper.getTemporaryWordListbyWordLength(3)
        for (word in tmpList) {
            assertTrue(word.length == 3)
        }
        tmpList = wordHelper.getTemporaryWordListbyWordLength(4)
        for (word in tmpList) {
            assertTrue(word.length == 4)
        }
        tmpList = wordHelper.getTemporaryWordListbyWordLength(5)
        for (word in tmpList) {
            assertTrue(word.length == 5)
        }
        tmpList = wordHelper.getTemporaryWordListbyWordLength(6)
        for (word in tmpList) {
            assertTrue(word.length == 6)
        }
        assertEquals(15290, tmpList.size)
        tmpList = wordHelper.getTemporaryWordListbyWordLength(7)
        assertEquals(0, tmpList.size)
        for (word in tmpList) {
            assertTrue(word.length == 7)
        }
    }

    @Test
    @Throws(Exception::class)
    fun testRemoveCharacter() {
        var testString = "whatever"
        assertEquals(
                "whatver",
                wordHelper.removeCharacter(testString, 'e')
        )
        assertEquals(
                "whtever",
                wordHelper.removeCharacter(testString, 'a')
        )
        assertEquals(
                "watever",
                wordHelper.removeCharacter(testString, 'h')
        )
        testString = "wtver"
        assertEquals(
                "wtvr",
                wordHelper.removeCharacter(testString, 'e')
        )
        testString = "wtvr"
        assertEquals(
                "wtv",
                wordHelper.removeCharacter(testString, 'r')
        )
    }

    @Test
    @Throws(Exception::class)
    fun testScoreWithLetter() {
        assertEquals(1, scoreMap.getScoreForLetter('a'))
        assertEquals(0, scoreMap.getScoreForLetter('4'))
        assertEquals(10, scoreMap.getScoreForLetter('q'))
        assertEquals(10, scoreMap.getScoreForLetter('z'))
        assertEquals(8, scoreMap.getScoreForLetter('x'))
        assertEquals(3, scoreMap.getScoreForLetter('p'))
    }

    @Test
    @Throws(Exception::class)
    fun testScoreWithWord() {
        assertEquals(16, scoreMap.getScoreForWord("hophead"))
        assertEquals(7, scoreMap.getScoreForWord("single"))
        assertEquals(7, scoreMap.getScoreForWord("score"))
        assertEquals(16, scoreMap.getScoreForWord("zebra"))
    }
}
