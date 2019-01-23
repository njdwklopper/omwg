package com.tegnatiek.omw.wordengine

import java.util.LinkedHashMap

class WordScoreMap {

    /**
     * Scrabble scores
     *
     * Point value is as follows:
     * (1 point)-A, E, I, O, U, L, N, S, T, R.
     * (2 points)-D, G.
     * (3 points)-B, C, M, P.
     * (4 points)-F, H, V, W, Y.
     * (5 points)-K.
     * (8 points)- J, X.
     * (10 points)-Q, Z.
     */

    private val scoreMap = LinkedHashMap<Char, Int>(24)

    init {
        initMap()
    }

    fun getScoreForLetter(letter: Char): Int {
        return if (scoreMap.containsKey(letter)) {
            scoreMap[letter]!!
        } else {
            0
        }
    }

    fun getScoreForWord(word: String): Int {
        var score = 0
        for (ch in word.toCharArray()) {
            score += getScoreForLetter(ch)
        }
        return score
    }

    private fun initMap() {
        scoreMap['a'] = 1
        scoreMap['b'] = 3
        scoreMap['c'] = 3
        scoreMap['d'] = 2
        scoreMap['e'] = 1
        scoreMap['f'] = 4
        scoreMap['g'] = 2
        scoreMap['h'] = 4
        scoreMap['i'] = 1
        scoreMap['j'] = 8
        scoreMap['k'] = 5
        scoreMap['l'] = 1
        scoreMap['m'] = 3
        scoreMap['n'] = 1
        scoreMap['o'] = 1
        scoreMap['p'] = 3
        scoreMap['q'] = 10
        scoreMap['r'] = 1
        scoreMap['s'] = 1
        scoreMap['t'] = 1
        scoreMap['u'] = 1
        scoreMap['v'] = 4
        scoreMap['w'] = 4
        scoreMap['x'] = 8
        scoreMap['y'] = 4
        scoreMap['z'] = 10
    }
}