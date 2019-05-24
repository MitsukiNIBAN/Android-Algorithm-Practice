package com.mitsuki.algorithm.chessboard

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.mitsuki.algorithm.R
import kotlinx.android.synthetic.main.activity_binary_search.*
import kotlinx.android.synthetic.main.activity_binary_search.initBtn
import kotlinx.android.synthetic.main.activity_chessboard.*
import java.lang.Exception

class ChessboardActivity : AppCompatActivity() {

    private lateinit var board: Array<IntArray>
    private var thread: HandlerThread = HandlerThread("BinarySearch")
    private lateinit var handler: Handler
    private lateinit var mainHandler: Handler
    private var k: Int = 0
    private var side: Int = 0
    private var mark: Int = 0

    private var colorGap = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chessboard)

        title = "棋盘覆盖"

        thread.start()
        handler = Handler(thread.looper)
        mainHandler = Handler(mainLooper)

        initBtn.setOnClickListener {
            try {
                mark = 0
                k = Integer.parseInt(kValue.text.toString())
                side = Math.pow(2.0, k.toDouble()).toInt()
                colorGap = (side * side - 1) / 3
                board = Array(side) { IntArray(side) }
                chessboard.removeAllViews()

                var lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.0F)
                var vp = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0F)

                for (i in board) {
                    var linearLayout = LinearLayout(this)
                    linearLayout.layoutParams = lp
                    linearLayout.orientation = LinearLayout.HORIZONTAL
                    for (j in i) {
                        var view = View(this)
                        view.layoutParams = vp
                        view.setBackgroundColor(Color.rgb(240, 240, 240))
                        linearLayout.addView(view)
                    }
                    chessboard.addView(linearLayout)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        coverBtn.setOnClickListener {
            handler.post {

                try {
                    var x = Integer.parseInt(spXValue.text.toString())
                    var y = Integer.parseInt(spYValue.text.toString())
                    setText(y, x, 0)
                    cover(x, y, 0, 0, side)
                } catch (e: Exception) {

                }
            }
        }

    }


    private fun cover(spX: Int, spY: Int, x: Int, y: Int, s: Int) {

        //首先进行棋盘分割并进行特殊格子位置判断
        var cX = x + s / 2
        var cY = y + s / 2

        var mode = if (spX < cX) {
            if (spY < cY) 1 else 3
        } else {
            if (spY < cY) 2 else 4
        }

        if (s == 2) {
            //此时为最小棋盘
            mark++
            when (mode) {
                1 -> {
                    board[y][x + 1] = mark
                    board[y + 1][x] = mark
                    board[y + 1][x + 1] = mark

                    setText(y, x + 1, mark)
                    setText(y + 1, x, mark)
                    setText(y + 1, x + 1, mark)
                }
                2 -> {
                    board[y][x] = mark
                    board[y + 1][x] = mark
                    board[y + 1][x + 1] = mark

                    setText(y, x, mark)
                    setText(y + 1, x, mark)
                    setText(y + 1, x + 1, mark)
                }
                3 -> {
                    board[y][x + 1] = mark
                    board[y][x] = mark
                    board[y + 1][x + 1] = mark

                    setText(y, x + 1, mark)
                    setText(y, x, mark)
                    setText(y + 1, x + 1, mark)
                }
                4 -> {
                    board[y][x + 1] = mark
                    board[y + 1][x] = mark
                    board[y][x] = mark

                    setText(y, x + 1, mark)
                    setText(y + 1, x, mark)
                    setText(y, x, mark)
                }
            }
            Thread.sleep(500)
            return
        }

        mark++
        if (mode != 1) {
            board[cY - 1][cX - 1] = mark
            setText(cY - 1, cX - 1, mark)
        }
        if (mode != 2) {
            board[cY - 1][cX] = mark
            setText(cY - 1, cX, mark)
        }
        if (mode != 3) {
            board[cY][cX - 1] = mark
            setText(cY, cX - 1, mark)
        }
        if (mode != 4) {
            board[cY][cX] = mark
            setText(cY, cX, mark)
        }

        Thread.sleep(500)

        //四分棋盘，分别覆盖
        cover(if (mode == 1) spX else cX - 1, if (mode == 1) spY else cY - 1, x, y, s / 2)
        cover(if (mode == 2) spX else cX, if (mode == 2) spY else cY - 1, cX, y, s / 2)
        cover(if (mode == 3) spX else cX - 1, if (mode == 3) spY else cY, x, cY, s / 2)
        cover(if (mode == 4) spX else cX, if (mode == 4) spY else cY, cX, cY, s / 2)
    }

    private fun setText(y: Int, x: Int, mark: Int) {
        mainHandler.post {
            var color: Float = mark * 6F / colorGap
            var red = 0
            var green = 0
            var blue = 0

            when (color.toInt()) {
                0 -> {
                    red = 255
                    green = (color * 255).toInt()
                    blue = 0
                }
                1 -> {
                    red = ((2 - color) * 255).toInt()
                    green = 255
                    blue = 0
                }
                2 -> {
                    red = 0
                    green = 255
                    blue = ((color - 2) * 255).toInt()
                }
                3 -> {
                    red = 0
                    green = ((4 - color) * 255).toInt()
                    blue = 255
                }
                4 -> {
                    red = ((color - 4) * 255).toInt()
                    green = 0
                    blue = 255
                }
                5 -> {
                    red = 255
                    green = 0
                    blue = ((6 - color) * 255).toInt()
                }
                6 ->{
                    red = 255
                    green = 0
                    blue = ((6 - color) * 255).toInt()
                }
            }
            ((chessboard.getChildAt(y) as LinearLayout).getChildAt(x) as View).setBackgroundColor(Color.rgb(red, green, blue))
        }
    }
}