package com.mitsuki.algorithm.fractal

import android.graphics.Color
import android.os.Build.VERSION_CODES.BASE
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.MotionEventCompat
import android.util.Log
import android.view.MotionEvent
import com.mitsuki.algorithm.helper.TaskThreadPoolExecutor
import kotlinx.android.synthetic.main.activity_fractal.*
import java.lang.Exception
import java.util.*
import java.util.concurrent.TimeUnit
import android.widget.Toast
import java.util.concurrent.LinkedBlockingQueue
import android.view.GestureDetector
import android.view.ScaleGestureDetector
import android.view.View

class FractalActivity : AppCompatActivity() {

    private val THRESHOLD = 200
    private val RENDER_BLOCK_SIZE = 100

    private lateinit var taskPool: TaskThreadPoolExecutor
    private lateinit var c: Complex
    private lateinit var myGestureListener: MyGestureListener
    private lateinit var detector: GestureDetector
    private lateinit var scaleDetector: ScaleGestureDetector

    private var color: MutableList<Int> = ArrayList()
    private var centerX = 0.0
    private var centerY = 0.0
    private var base = 400.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mitsuki.algorithm.R.layout.activity_fractal)
        title = "分形"

        myGestureListener = object : MyGestureListener() {
            override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
                centerX -= distanceX
                centerY -= distanceY
                return false
            }

            override fun onScale(detector: ScaleGestureDetector?): Boolean {
                base *= (detector ?: return true).scaleFactor
                return true
            }
        }

        detector = GestureDetector(this, myGestureListener)
        scaleDetector = ScaleGestureDetector(this, myGestureListener)
        sketchpad.setOnTouchListener { _, event ->
            when (event.pointerCount) {
                1 -> {
                    if (event.actionMasked == MotionEvent.ACTION_UP) {
                        taskPool.resume()
//                        Log.e("sdf", "$centerX  $centerY")
                        performTask()
                    } else if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                        taskPool.pause()
                    }
                    detector.onTouchEvent(event)
                }
                2 -> scaleDetector.onTouchEvent(event)
                else -> false
            }
        }

        sketchpad.post {
            centerX = sketchpad.width / 2.0
            centerY = sketchpad.height / 2.0
            taskPool = TaskThreadPoolExecutor(4, 30, 500, TimeUnit.MILLISECONDS, LinkedBlockingQueue())
            initBtn.isEnabled = true
        }

        initBtn.setOnClickListener {
            drawBtn.isEnabled = false
            taskPool.execute {
                try {
                    var cr = cReal.text.toString().toDouble()
                    var ci = cImaginary.text.toString().toDouble()
                    c = Complex(cr, ci)

                    color.clear()
                    var cg = colorGradient.text.toString().toFloat()
                    for (i in 0..THRESHOLD) {
                        var h = i * 360 / cg
                        color.add(Color.HSVToColor(floatArrayOf(h % 360, 100F, 50F)))
                    }
                    runOnUiThread {
                        drawBtn.isEnabled = true
                        Toast.makeText(this, "init success", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        drawBtn.isEnabled = false
                        Toast.makeText(this, "init fail", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        drawBtn.setOnClickListener {
            performTask()
        }

    }

    private fun performTask() {
        var xCount = sketchpad.width / RENDER_BLOCK_SIZE + if (sketchpad.width % RENDER_BLOCK_SIZE > 0) 1 else 0
        var yCount = sketchpad.height / RENDER_BLOCK_SIZE + if (sketchpad.height % RENDER_BLOCK_SIZE > 0) 1 else 0
        for (i in 0 until xCount * yCount) {
            taskPool.execute {
                var x = i % xCount * RENDER_BLOCK_SIZE
                var y = i / xCount * RENDER_BLOCK_SIZE
                performRender(x, y,
                        if (x + RENDER_BLOCK_SIZE - 1 >= sketchpad.width) sketchpad.width - 1 else x + RENDER_BLOCK_SIZE - 1,
                        if (y + RENDER_BLOCK_SIZE - 1 >= sketchpad.height) sketchpad.height - 1 else y + RENDER_BLOCK_SIZE - 1)

                runOnUiThread {
                    sketchpad.invalidate()
                }
            }
        }
    }

    private fun performRender(left: Int, top: Int, right: Int, bottom: Int) {
        for (y in top..bottom) {
            for (x in left..right) {
                var re = (x - centerX) / base
                var im = (y - centerY) / base
                var it = julia(Complex(re, im))
                var color = if (it == -1) Color.rgb(28, 47, 55) else color[it]
                sketchpad.bitmap.setPixel(x, y, color)
            }
        }
    }

    private fun julia(value: Complex): Int {
        var temp: Complex = value
        var count = 0
        while (temp.mod() <= 2) {
            temp = temp * temp + c
            count++
            if (count > THRESHOLD) return -1
        }
        return count
    }

    override fun onDestroy() {
        sketchpad.recycler()
        taskPool.pause()
        taskPool.shutdownNow()
        super.onDestroy()
    }
}
