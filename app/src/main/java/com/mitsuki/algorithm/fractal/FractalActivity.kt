package com.mitsuki.algorithm.fractal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import com.mitsuki.algorithm.R
import kotlinx.android.synthetic.main.activity_fractal.*

class FractalActivity : AppCompatActivity() {

    private var thread: HandlerThread = HandlerThread("Fractal")
    private lateinit var handler: Handler
    private lateinit var mainHandler: Handler

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fractal)
        title = "分形艺术"

        thread.start()
        handler = Handler(thread.looper)
        mainHandler = Handler(mainLooper)


        var ttt = 1.0

        test.setOnClickListener {
            handler.post {
                count = 0
                ttt =  julia(ttt)
                Log.e("julia", "value:$ttt")
            }
        }
    }

    private fun julia(value: Double): Double {
        return Math.pow(value, 2.0) - 0.75
    }
}
