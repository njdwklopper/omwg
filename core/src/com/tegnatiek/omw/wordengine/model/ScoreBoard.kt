package com.tegnatiek.omw.wordengine.model

class ScoreBoard {
    var totalScore: Long = 0
    var lastCorrectWordScore: Long = 0
    var totalWordsFound = 0
    var totalWordsNotFound: Int = 0
    var totalWordsToGo: Int = 0
    var totalWordsAvailable: Int = 0
}