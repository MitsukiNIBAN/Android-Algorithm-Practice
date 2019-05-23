package com.mitsuki.algorithm.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class ChineseRingView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var gap: Float = 0.0f
    var ringList: MutableList<Boolean>? = null


    init {
        paint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var s = (ringList ?: return).size - 1
        gap = ((width - 64 * 2 - 96) / if (s == 0) 1 else s).toFloat()

        paint.strokeWidth = 8F

        paint.color = Color.rgb(200, 100, 100)
        for (i in 0 until (ringList ?: return).size) {

            var state = if ((ringList ?: return)[i]) {
                if (i == (ringList ?: return).size - 1) {
                    3
                } else {
                    if ((ringList ?: return)[i + 1]) {
                        3
                    } else {
                        2
                    }
                }
            } else {
                1
            }

            drawPile((ringList ?: return).size - 1 - i, state, canvas)
            drawBall((ringList ?: return).size - 1 - i, state, canvas)
        }

        paint.color = Color.rgb(100, 100, 100)
        canvas?.drawLine(32F, (height / 3).toFloat(), (width - 32).toFloat(), (height / 3).toFloat(), paint)
        paint.color = Color.rgb(100, 100, 200)
        canvas?.drawLine(32F, (height / 3 * 2).toFloat(), (width - 128).toFloat(), (height / 3 * 2).toFloat(), paint)

        paint.color = Color.rgb(100, 200, 100)
        for (i in 0 until (ringList ?: return).size) {
            var state = if ((ringList ?: return)[i]) {
                if (i == (ringList ?: return).size - 1) {
                    3
                } else {
                    if ((ringList ?: return)[i + 1]) {
                        3
                    } else {
                        2
                    }
                }
            } else {
                1
            }

            drawRing((ringList ?: return).size - 1 - i, state, canvas)
        }
    }

    private fun drawPile(i: Int, state: Int, canvas: Canvas?) {
        when (state) {
            1 -> {
                canvas?.drawLine(
                    64F + gap * i, (height / 4).toFloat(),
                    64F + gap * i, (height / 3 * 2).toFloat(), paint
                )
            }
            2 -> {
                canvas?.drawLine(
                    64F + gap * i, (height / 8 * 3).toFloat(),
                    64F + gap * i, (height / 24 * 19).toFloat(), paint
                )
            }
            3 -> {
                canvas?.drawLine(
                    64F + gap * i, (height / 2).toFloat(),
                    64F + gap * i, (height / 12 * 11).toFloat(), paint
                )
            }
        }
    }

    private fun drawBall(i: Int, state: Int, canvas: Canvas?) {
        when (state) {
            1 -> {
                canvas?.drawCircle(64F + gap * i, (height / 3 * 2 + 16).toFloat(), 16F, paint)
            }
            2 -> {
                canvas?.drawCircle(64F + gap * i, (height / 24 * 19 + 16).toFloat(), 16F, paint)
            }
            3 -> {
                canvas?.drawCircle(64F + gap * i, (height / 12 * 11 + 16).toFloat(), 16F, paint)
            }
        }
    }

    private fun drawRing(i: Int, state: Int, canvas: Canvas?) {
        when (state) {
            1 -> {
                canvas?.drawLine(
                    64F + gap * i, (height / 4).toFloat(),
                    (64F + gap * (i + 1.3)).toFloat(), (height / 2).toFloat(), paint
                )
            }
            2 -> {
                canvas?.drawLine(
                    64F + gap * i, (height / 8 * 3).toFloat(),
                    (64F + gap * (i + 1.3)).toFloat(), (height / 8 * 5).toFloat(), paint
                )
            }
            3 -> {
                canvas?.drawLine(
                    64F + gap * i, (height / 2).toFloat(),
                    (64F + gap * (i + 1.6)).toFloat(), (height / 3 * 2).toFloat(), paint
                )
            }
        }
    }
}