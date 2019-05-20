package com.mitsuki.algorithm.hanoi

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.View

class PileView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var hanoiList: MutableList<Dish>? = null

    private val pile: Int = 8
    private val maxGap: Int = 8
    private val miniDishW = 16
    private val dishH = 8F
    private val paddingBottom = 4F
    private val verGap = 2F

    init {
        paint.style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = Color.rgb(0, 0, 0)
        paint.strokeWidth = (pile * 2).toFloat()
        canvas?.drawLine(
                (width / 2).toFloat(), 0F,
                (width / 2).toFloat(), height.toFloat(), paint
        )

        var gap: Int = if ((width - miniDishW) / 2 - maxGap * (hanoiList?.size ?: return) > 0)
            maxGap else 2

        paint.strokeWidth = dishH

        for ((count, dish) in (hanoiList ?: return).withIndex()) {
            paint.color = dish.color
            canvas?.drawLine((gap * (dish.number - 1)).toFloat(), (height - count * (verGap + dishH) - paddingBottom),
                    (width - gap * (dish.number - 1)).toFloat(), (height - count * (verGap + dishH) - paddingBottom),
                    paint)
        }

    }

}