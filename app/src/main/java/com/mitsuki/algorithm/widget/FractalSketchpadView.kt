package com.mitsuki.algorithm.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.lang.Exception

class FractalSketchpadView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    //    var bitmap: MutableList<Bitmap> = ArrayList()
    lateinit var bitmap: Bitmap
    var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        try {
            canvas?.drawBitmap(bitmap, 0F, 0F, paint)
        } catch (e: Exception) {

        }
    }

    fun recycler() {
        if (!bitmap.isRecycled) {
            bitmap.recycle()
        }
    }
}