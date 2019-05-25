package com.mitsuki.algorithm.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class FractalSketchpadView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var bitmap: MutableList<Bitmap> = ArrayList()
    var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (bitmap.size != 16) return

        for (i in 0 until 16) {
            canvas?.drawBitmap(bitmap[i],
                    width / 4F * (i % 4),
                    height / 4F * (i / 4), paint)
//            canvas?.drawText((i + 1).toString(), width / 4F * (i % 4), height / 4F * (i / 4) + paint.textSize, paint)
        }
    }
}