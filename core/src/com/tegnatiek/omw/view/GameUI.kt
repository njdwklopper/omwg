package com.tegnatiek.omw.view

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.tegnatiek.omw.GamePlayController
import com.tegnatiek.omw.GamePlayController.Companion.STATE_GAME_LOADING_OTHER_ASSETS
import com.tegnatiek.omw.GamePlayController.Companion.STATE_GAME_LOAD_SPRITE_ATLAS
import com.tegnatiek.omw.GamePlayController.Companion.STATE_GAME_PLAY
import com.tegnatiek.omw.GamePlayController.Companion.STATE_GAME_START
import com.tegnatiek.omw.wordengine.WordHelper
import com.tegnatiek.omw.wordio.letters.LetterSprites

class GameUI(private val width: Float, private val height: Float) {

    companion object {
        const val WORD_BOARD_SCALE = 0.34f
        const val WORD_SHUFFLE_SCALE = 0.95f
        const val BOARD_HEIGHT = 250
    }

    private val batch: SpriteBatch = SpriteBatch()

    private var columnDelta: Float = 0.toFloat()
    private var columnWidth: Float = 0.toFloat()
    private val padding = 20.0f

    private val letterSprites: LetterSprites = LetterSprites(0.3f)
    private var controller: GamePlayController = GamePlayController()
    private var wordViews: ArrayList<WordView> = ArrayList()


    fun render(camera: OrthographicCamera) {
        when (controller.gameState) {
            STATE_GAME_START -> controller.changeStateAfterLoadingImageComplete()
            STATE_GAME_LOADING_OTHER_ASSETS -> {
                controller.refreshBoard()
                controller.changeStateToLoadSpriteAtlas()
            }
            STATE_GAME_LOAD_SPRITE_ATLAS -> {
                for (word in controller.boardModel.getGameBoard()) {
                    val w = word.value
                    w.pos = Vector2(0f, 10f)
                    val wordView = WordView(w, letterSprites)
                    wordViews.add(wordView)
                }
                controller.changeStateToPlay()
            }
            STATE_GAME_PLAY -> {
                batch.projectionMatrix = camera.combined
                batch.begin()
                wordViews[0].draw(batch)
                batch.end()
            }
        }
    }

    private fun setupCoordinatesForWords() {
        for (c in 0 until WordHelper.MAX_COLUMNS) {
            for (r in 0 until WordHelper.MAX_ROWS) {

            }
        }
        println("Letters: ")

        for (word in controller.boardModel.getGameBoard()!!.values()) {
            println("\nWord: ")
            for (letter in word.wordLetters) {
                print("" + letter.letter)
            }
        }
    }

    private fun calculateColumnSpread() {
        val widthWithPadding = width - padding * 2f
        columnWidth = widthWithPadding / WordHelper.MAX_COLUMNS
        columnDelta = padding * 2f
//        wordHeight = textHeight
    }

    private fun drawWordMarkers(c: Int, r: Int) {
//        font.data.setScale(0.34f)
//        font.draw(batch, "A B C D E", columnDelta + columnWidth * c.toFloat(), height - BOARD_HEIGHT - wordHeight * r)
    }

    fun destroy() {
        controller.destroy()
    }
}