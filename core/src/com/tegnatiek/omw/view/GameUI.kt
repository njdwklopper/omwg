package com.tegnatiek.omw.view

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.tegnatiek.omw.GamePlayController
import com.tegnatiek.omw.GamePlayController.Companion.STATE_GAME_LOADING_OTHER_ASSETS
import com.tegnatiek.omw.GamePlayController.Companion.STATE_GAME_PLAY
import com.tegnatiek.omw.GamePlayController.Companion.STATE_GAME_START
import com.tegnatiek.omw.wordengine.WordHelper
import com.tegnatiek.omw.wordengine.model.Letter
import com.tegnatiek.omw.wordengine.model.Word

class GameUI(private val width: Float, private val height: Float) {

    companion object {
        const val WORD_BOARD_SCALE = 0.34f
        const val WORD_SHUFFLE_SCALE = 0.95f
        const val BOARD_HEIGHT = 250
    }

    private var wordHeight = 50f

    private val batch: SpriteBatch = SpriteBatch()
    private lateinit var font: BitmapFont

    private var columnDelta: Float = 0.toFloat()
    private var columnWidth: Float = 0.toFloat()
    private val padding = 20.0f

    private var controller: GamePlayController = GamePlayController()

    private val textWidth: Float
        get() {
            val layout = GlyphLayout()
            layout.setText(font, "_ _ _ _ _")
            return layout.width
        }

    private val textHeight: Float
        get() {
            val layout = GlyphLayout()
            font.data.setScale(0.34f)
            layout.setText(font, "X")
            return layout.height * 2
        }

    fun render(camera: OrthographicCamera) {
        when (controller.gameState) {
            STATE_GAME_START -> controller.changeStateAfterLoadingImageComplete()
            STATE_GAME_LOADING_OTHER_ASSETS -> {
                //TODO loading image + loading other assets
                //                batch.begin();
                //                assetLoader.getFont().draw(batch, "Loading...", width / 2, height / 2);
                //                batch.end();
                //                if (assetLoader.getManager().isLoaded("data/NEWWORD.LST")) {
                //                    controller.refreshBoard();
                //                    gameState = STATE_GAME_PLAY;
                //                }
                font = controller.getFont()
                calculateColumnSpread()
                controller.refreshBoard()
                setupCoordinatesForWords()
                controller.changeStateToGamePlay()
            }
            STATE_GAME_PLAY -> {
                batch.projectionMatrix = camera.combined
                batch.begin()
                //                font.draw(batch, "\n_ _ _\nH _ L\nHELLO\nWORLD", 10, height - 40);
                for (c in 0 until WordHelper.MAX_COLUMNS) {
                    for (r in 0 until WordHelper.MAX_ROWS) {
                        drawWordMarkers(c, r)
                    }
                }
                batch.end()
            }
        }
    }

    private fun setupCoordinatesForWords() {
        val layout = GlyphLayout()
        font.data.setScale(0.34f)
        layout.setText(font, "X")

        for (c in 0 until WordHelper.MAX_COLUMNS) {
            for (r in 0 until WordHelper.MAX_ROWS) {

            }
        }
        println("Letters: ")

        for (word in controller.boardModel.getGameBoard()!!.values()) {
            println("\nWord: ")
            for (letter in word.wordLetters!!) {
                print("" + letter.letter)
            }
        }
    }

    private fun calculateColumnSpread() {
        val widthWithPadding = width - padding * 2f
        columnWidth = widthWithPadding / WordHelper.MAX_COLUMNS
        columnDelta = padding * 2f
        wordHeight = textHeight
        //        columnDelta = columnWidth * ((float) WordHelper.MAX_COLUMNS / ((float) WordHelper.MAX_COLUMNS - 1));
    }

    private fun drawWordMarkers(c: Int, r: Int) {
        font.data.setScale(0.34f)
        font.draw(batch, "A B C D E", columnDelta + columnWidth * c.toFloat(), height - BOARD_HEIGHT - wordHeight * r)
    }

    fun destroy() {
        controller.destroy()
    }
}