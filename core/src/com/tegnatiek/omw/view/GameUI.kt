package com.tegnatiek.omw.view

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
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

    private var wordHeight = 50f

    private val batch: SpriteBatch = SpriteBatch()
    private lateinit var font: BitmapFont

    private var columnDelta: Float = 0.toFloat()
    private var columnWidth: Float = 0.toFloat()
    private val padding = 20.0f

    private val letterSprites: LetterSprites = LetterSprites(0.3f)
    private var controller: GamePlayController = GamePlayController()
    private var wordViews: ArrayList<WordView> = ArrayList()

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
                //                batch.end();
                //                if (assetLoader.getManager().isLoaded("data/NEWWORD.LST")) {
                //                    controller.refreshBoard();
                //                    gameState = STATE_GAME_PLAY;
                //                }
                font = controller.getFont()
                batch.begin()
                font.draw(batch, "Loading...", width / 2, height / 2)
                batch.end()
                calculateColumnSpread()
                controller.refreshBoard()
                setupCoordinatesForWords()
                controller.changeStateToPlay()
            }
            STATE_GAME_LOAD_SPRITE_ATLAS -> {
//                for (word in controller.boardModel.getGameBoard()) {
//                    val w = word.value
//                    w.pos = Vector2(0f, 10f)
//                    val wordView = WordView(w, letterSprites)
//                    wordViews.add(wordView)
//                }
                controller.changeStateToPlay()
            }
            STATE_GAME_PLAY -> {
                batch.projectionMatrix = camera.combined
                batch.begin()
                //                font.draw(batch, "\n_ _ _\nH _ L\nHELLO\nWORLD", 10, height - 40);

//                wordViews[0].draw(batch)
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
        font.data.setScale(0.36f)
        layout.setText(font, "_")

        for (c in 0 until WordHelper.MAX_COLUMNS) {
            for (r in 0 until WordHelper.MAX_ROWS) {

            }
        }
        println("Letters: ")

        for (word in controller.boardModel.getGameBoard().values()) {
            println("\nWord: ")
            for (letter in word.wordLetters) {
                print("" + letter.letter)
            }
        }
    }

    private fun calculateColumnSpread() {
        val widthWithPadding = width - (padding * 2f)
        columnWidth = widthWithPadding / WordHelper.MAX_COLUMNS
        columnDelta = padding * 2f
        wordHeight = textHeight
    }

    private fun drawWordMarkers(c: Int, r: Int) {
        font.draw(batch, "_ _ H _ _ _", padding + columnDelta + (columnWidth * c), height - BOARD_HEIGHT - wordHeight * r)
    }

    fun destroy() {
        controller.destroy()
    }
}