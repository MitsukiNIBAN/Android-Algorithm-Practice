package com.mitsuki.algorithm.chinesering

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import com.mitsuki.algorithm.R
import kotlinx.android.synthetic.main.activity_chinese_ring.*
import kotlinx.android.synthetic.main.activity_hanoi.*
import kotlinx.android.synthetic.main.activity_hanoi.reset
import kotlinx.android.synthetic.main.activity_hanoi.start
import kotlinx.android.synthetic.main.activity_main.*

class ChineseRingActivity : AppCompatActivity() {

    private var list: MutableList<Boolean> = ArrayList()
    private var thread: HandlerThread = HandlerThread("ring")
    private lateinit var handler: Handler
    lateinit var mainHandler: Handler
    private var runnable = Runnable { operating(9, true) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chinese_ring)

        title = "Chinese Ring"

        thread.start()
        handler = Handler(thread.looper)
        mainHandler = Handler(mainLooper)
        ringView.ringList = list


        reset.setOnClickListener {
            list.clear()
            for (i in 1..9) {
                list.add(false)
            }
            ringView.invalidate()
            start.isEnabled = true
        }

        start.setOnClickListener {
            start.isEnabled = false
            handler.post(runnable)
        }

    }

    //九连环数列递归
    private fun sequence(i: Int): Int {
        return if (i < 1) 0
        else if (i == 1) 1
        else {
            if (i % 2 == 0) sequence(i - 1) * 2
            else sequence(i - 1) * 2 + 1
        }
    }

    //但实际的九连环前两个环能同时取下，存在一定的优化步骤数
    private fun optimization(i: Int): Int {
        return if (i <= 1) 0
        else if (i == 2) 1
        else {
            if (i % 2 == 0) optimization(i - 1) * 2 + 1
            else optimization(i - 1) * 2 - 1
        }
    }

    //九连环运算递归
    private fun ring(i: Int): Int {
        return when {
            i < 1 -> 0
            i == 1 -> 1
            i == 2 -> 1     //优化的步骤
            else -> ring(i - 2) * 2 + 1 + ring(i - 1)
        }
    }

    //九连环操作递归
    private fun operating(i: Int, unload: Boolean) {
        when {
            i < 1 -> return
            i == 1 -> opRing(1, unload)
            i == 2 -> opRing(-1, unload)
            else -> {
                if (unload) {
                    operating(i - 2, unload)
                    opRing(i, unload)
                    operating(i - 2, !unload)
                    operating(i - 1, unload)
                } else {
                    operating(i - 1, unload)
                    operating(i - 2, !unload)
                    opRing(i, unload)
                    operating(i - 2, unload)
                }
            }
        }
    }

    private fun opRing(i: Int, unload: Boolean) {
        if (i == -1) {
            list[0] = unload
            list[1] = unload
        } else {
            list[i - 1] = unload
        }
        mainHandler.post { ringView.invalidate() }
        Thread.sleep(200)
    }
}
