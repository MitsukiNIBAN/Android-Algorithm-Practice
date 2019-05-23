package com.mitsuki.algorithm.quicksort

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import com.mitsuki.algorithm.R
import com.mitsuki.algorithm.helper.OutOfOrderUtils.upset
import kotlinx.android.synthetic.main.activity_binary_search.*
import kotlinx.android.synthetic.main.activity_binary_search.initBtn
import kotlinx.android.synthetic.main.activity_sort.*
import java.util.*

class QuickSortActivity : AppCompatActivity() {

    private val COUNT = 200
    private var thread: HandlerThread = HandlerThread("QuickSort")
    private lateinit var handler: Handler
    private lateinit var mainHandler: Handler
    private var random = Random()
    var sequence: IntArray = IntArray(COUNT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sort)

        title = "快速排序"

        thread.start()
        handler = Handler(thread.looper)
        mainHandler = Handler(mainLooper)

        initBtn.setOnClickListener {
            handler.post {
                for (i in 1..COUNT) {
                    sequence[i - 1] = i
                }
                upset(sequence, 0, COUNT - 1, random)
                sequenceView.sequence = sequence

                mainHandler.post {
                    sequenceView.invalidate()
                }
            }
        }

        sortBtn.setOnClickListener {
            handler.post {
                sort(sequence, 0, COUNT - 1)
                Log.e("1", "1")
            }
        }
    }

    //普通快速排序
    private fun sort(array: IntArray, start: Int, end: Int) {
        var count = end - start + 1
        if (count < 2) return

        var sub = start
        var head = start + 1
        var tail = end
        var tag = true

        //治
        for (i in 1 until count) {
            if (tag) {
                reload(sub, tail)
                if (array[sub] > array[tail]) {
                    var temp = array[sub]
                    array[sub] = array[tail]
                    array[tail] = temp
                    reload(tail, sub)
                    sub = tail
                    tag = false
                }
                tail--
            } else {
                reload(sub, head)
                if (array[sub] < array[head]) {
                    var temp = array[sub]
                    array[sub] = array[head]
                    array[head] = temp
                    reload(head, sub)
                    sub = head
                    tag = true
                }
                head++
            }
        }

        //分
        sort(array, start, sub - 1)
        sort(array, sub + 1, end)
    }

    private fun reload(n: Int, t: Int) {
        sequenceView.n = n
        sequenceView.t = t
        mainHandler.post {
            sequenceView.invalidate()
        }
        Thread.sleep(100)
    }
}