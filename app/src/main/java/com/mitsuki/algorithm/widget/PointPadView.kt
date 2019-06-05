package com.mitsuki.algorithm.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.mitsuki.algorithm.closestpair.Pair
import java.lang.Exception

class PointPadView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var pointSet: MutableList<Point>? = null
    var start = 0
    var end = 0
    var p1: Point? = null
    var p2: Point? = null

    var best: Pair? = null

    var temp: MutableList<Point>? = null

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 8f

    }

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for (i in 0 until (pointSet ?: return).size) {
            paint.color = when {
                (pointSet ?: return)[i] == p1 || (pointSet ?: return)[i] == p2 -> Color.YELLOW
                i in start..end -> Color.RED
                else -> Color.BLACK
            }

            if (temp != null) {
                if ((temp ?: return).contains((pointSet ?: return)[i]))
                    paint.color = Color.GREEN
            }


            canvas?.drawPoint((pointSet ?: return)[i].x.toFloat(),
                    (pointSet ?: return)[i].y.toFloat(), paint)

            if (p1 != null && p2 != null) {
                paint.color = Color.YELLOW
                var sx = (p1 ?: return).x
                var sy = (p1 ?: return).y
                var ex = (p2 ?: return).x
                var ey = (p2 ?: return).y
                canvas?.drawLine(sx.toFloat(), sy.toFloat(), ex.toFloat(), ey.toFloat(), paint)
            }

            if (best != null) {
                paint.color = Color.RED
                var sx = (best ?: return).p1.x
                var sy = (best ?: return).p1.y
                var ex = (best ?: return).p2.x
                var ey = (best ?: return).p2.y
                canvas?.drawLine(sx.toFloat(), sy.toFloat(), ex.toFloat(), ey.toFloat(), paint)
            }


        }
    }


}