package com.mitsuki.algorithm.binarysearch

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.mitsuki.algorithm.R
import kotlinx.android.synthetic.main.activity_binary_search.*
import kotlinx.android.synthetic.main.activity_hanoi.*

class BinarySearchActivity : AppCompatActivity() {

    private var orderedList: MutableList<Int> = ArrayList()
    private var thread: HandlerThread = HandlerThread("BinarySearch")
    private lateinit var handler: Handler
    private lateinit var mainHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_binary_search)
        title = "二分查找"

        thread.start()
        handler = Handler(thread.looper)
        mainHandler = Handler(mainLooper)

        initBtn.setOnClickListener {
            if (!TextUtils.isEmpty(count.text)) {
                orderedList.clear()
                list.removeAllViews()
                var params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.0f)
                params.setMargins(0, 1, 0, 0)
                for (i in 1..Integer.parseInt(count.text.toString())) {
                    orderedList.add(i)
                    var temp = View(this)
                    temp.layoutParams = params
                    temp.setBackgroundColor(Color.rgb(255, 255, 255))
                    list.addView(temp)
                }

                findBtn.isEnabled = true
            }
        }

        findBtn.setOnClickListener {
            if (!TextUtils.isEmpty(target.text)) {
                findBtn.isEnabled = false
                handler.post {
                    var tar = Integer.parseInt(target.text.toString())
                    searchForUI(tar, 0, orderedList.size - 1)
                }
            }
        }
    }

    //二分查找
    private fun search(target: Int, start: Int, end: Int): Int {
        when {
            start > end -> return -1
            start < 0 -> return -1
            start == end -> return if (orderedList[start] == target) start else -1
        }

        var centerPosition = (end - start) / 2 + start

        return when {
            orderedList[centerPosition] > target -> search(target, start, centerPosition - 1)
            orderedList[centerPosition] < target -> search(target, centerPosition + 1, end)
            else -> centerPosition
        }
    }

    //二分查找(增加UI控制)
    private fun searchForUI(target: Int, start: Int, end: Int) {
        when {
            start > end -> reDrawView(-1, -1)
            start < 0 -> reDrawView(-1, -1)
            start == end -> return if (orderedList[start] == target) reDrawView(start, end) else reDrawView(-1, -1)
        }

        reDrawView(start, end)
        Thread.sleep(1000)

        var centerPosition = (end - start) / 2 + start

        return when {
            orderedList[centerPosition] > target -> searchForUI(target, start, centerPosition - 1)
            orderedList[centerPosition] < target -> searchForUI(target, centerPosition + 1, end)
            else -> reDrawView(centerPosition, centerPosition)
        }
    }

    private fun reDrawView(s: Int, e: Int) {
        mainHandler.post {
            for (i in 0 until list.childCount) {
                if (i in s..e) {
                    list.getChildAt(i).setBackgroundColor(Color.rgb(250, 218, 69))
                } else {
                    list.getChildAt(i).setBackgroundColor(Color.rgb(255, 255, 255))
                }
            }
        }
    }
}
