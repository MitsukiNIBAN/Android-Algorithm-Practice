package com.mitsuki.algorithm.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class SequenceView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var sequence: IntArray? = null
    var horizontalGap: Float = 0F
    var minWidth = 20F

    var s = 0
    var e = -1

    var n = -1
    var t = -1

    init {
        paint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        horizontalGap = (width.toFloat() - minWidth) / (sequence ?: return).size
        var blockHeight = height.toFloat() / (sequence ?: return).size

        if (e >= 0) {
            paint.color = Color.rgb(252, 249, 135)
            canvas?.drawRect(0F, s * blockHeight,
                    width.toFloat(), blockHeight * (e + 1),
                    paint)
        }

        paint.color = Color.rgb(183, 232, 232)
        for (i in 0 until (sequence ?: return).size) {
            canvas?.drawRect(0F,
                    i * blockHeight,
                    minWidth + horizontalGap * ((sequence ?: return)[i] - 1),
                    blockHeight * (i + 1),
                    paint)
        }

        if (n >= 0) {
            paint.color = Color.rgb(252, 249, 135)
            canvas?.drawRect(0F, n * blockHeight,
                minWidth + horizontalGap * ((sequence ?: return)[n] - 1), blockHeight * (n + 1),
                    paint)
        }
        if (t >= 0) {
            paint.color = Color.rgb(255, 140, 140)
            canvas?.drawRect(0F, t * blockHeight,
                minWidth + horizontalGap * ((sequence ?: return)[t] - 1), blockHeight * (t + 1),
                    paint)
        }
    }
}