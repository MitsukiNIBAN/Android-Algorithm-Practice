package com.mitsuki.algorithm.fractal

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import com.mitsuki.algorithm.R
import kotlinx.android.synthetic.main.activity_fractal.*
import java.util.*

class FractalActivity : AppCompatActivity() {

    private val C = Complex(-0.70176, -0.3842)
    private val THRESHOLD = 200

    private var thread: HandlerThread = HandlerThread("Fractal")
    private lateinit var handler: Handler
    private lateinit var mainHandler: Handler

    private var color: MutableList<Int> = ArrayList()
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fractal)
        title = "分形"

        thread.start()
        handler = Handler(thread.looper)
        mainHandler = Handler(mainLooper)


        sketchpad.post {
            draw.isEnabled = true
            handler.post {
                for (i in 0..THRESHOLD + 1) {
                    var h = i * 360 / 100F
                    color.add(Color.HSVToColor(floatArrayOf(h % 360, 100F, 50F)))
                }

                for (i in 0 until 16) {
                    var b = Bitmap.createBitmap(
                        (sketchpad.width / 4F).toInt(),
                        (sketchpad.height / 4F).toInt(), Bitmap.Config.ARGB_8888
                    )
                    sketchpad.bitmap.add(b)
                }
                mainHandler.post {
                    sketchpad.invalidate()
                }
            }
        }

        draw.setOnClickListener {
            handler.post {
                for (i in 0 until 16) {
                    var b = sketchpad.bitmap[i]
                    var canvas = Canvas(b)
                    for (y in 0..b.height) {
                        for (x in 0..b.width) {
                            var re = (x + b.width * (i % 4)) / (sketchpad.width / 1.0) - 1
                            var im = (y + b.height * (i / 4)) / (sketchpad.width / 1.0) - 1
                            var iter = julia(Complex(re, im))
                            paint.color = color[iter + 1]
                            canvas.drawPoint(x.toFloat(), y.toFloat(), paint)
                        }
                    }
                    mainHandler.post {
                        sketchpad.invalidate()
                    }
                }
            }
        }
    }

    private fun julia(value: Complex): Int {
        var temp: Complex = value
        var count = 0
        while (temp.mod() <= 2) {
            temp = temp * temp + C
            count++
            if (count > THRESHOLD) return -1
        }
        return count
    }
}
