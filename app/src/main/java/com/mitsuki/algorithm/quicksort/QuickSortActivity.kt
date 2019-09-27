package com.mitsuki.algorithm.quicksort

import androidx.appcompat.app.AppCompatActivity
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

    private val COUNT = 400
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
                    sequence[i - 1] = random.nextInt(COUNT / 10) * 10
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
                sort2(sequence, 0, COUNT - 1)
            }
        }
    }

    //普通快速排序
    private fun sort(array: IntArray, start: Int, end: Int) {
        var count = end - start + 1
        if (count < 2) return

        var sub = start //基准数
        var head = start + 1
        var tail = end
        var tag = true

        //治
        for (i in 1 until count) {
            if (tag) {
                reload(sub, tail)
                if (array[sub] > array[tail]) {
                    swap(array, sub, tail)
                    reload(tail, sub)
                    sub = tail
                    tag = false
                }
                tail--
            } else {
                reload(sub, head)
                if (array[sub] < array[head]) {
                    swap(array, sub, head)
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

    //快速排序优化(三数取中 + 聚集相等元素)
    private fun sort2(array: IntArray, start: Int, end: Int) {
        var count = end - start + 1
        if (count < 2) return
        if (count < 3) {
            if (array[start] > array[end])
                swap(array, start, end)
            return
        }

        //三数取中确定枢轴
        if (array[count / 2] > array[end]) {
            if (array[count / 2] <= array[start]) {
                swap(array, start, count / 2)
            } else {
                if (array[start] < array[end]) {
                    swap(array, start, end)
                }
            }
        } else {
            if (array[count / 2] >= array[start]) {
                swap(array, start, count / 2)
            } else {
                if (array[start] > array[end]) {
                    swap(array, start, end)
                }
            }
        }

        var sub = start
        var head = start + 1
        var tail = end
        var tag = true

        for (i in 1 until count) {
            if (tag) {
                reload(sub, tail)
                if (array[sub] > array[tail]) {
                    swap(array, sub, tail)
                    reload(tail, sub)
                    sub = tail
                    tag = false
                }
                tail--
            } else {
                reload(sub, head)
                if (array[sub] < array[head]) {
                    swap(array, sub, head)
                    reload(head, sub)
                    sub = head
                    tag = true
                }
                head++
            }
        }

        //聚集相等元素
        var left = 0
        var right = 0
        for (i in start..end) {
            if (i == sub) continue
            if (i < sub) {
                reload(sub, sub - 1 - i + start)
                if (array[sub - 1 - i + start] == array[sub]) {
                    left++
                    reload(sub - 1 - i + start, sub - left)
                    swap(array, sub - 1 - i + start, sub - left)
                    reload(sub - left, sub - 1 - i + start)
                }
            }
            if (i > sub) {
                reload(sub, i)
                if (array[i] == array[sub]) {
                    right++
                    reload(i, sub + right)
                    swap(array, i, sub + right)
                    reload(sub + right, i)
                }
            }
        }

        sort2(array, start, sub - 1 - left)
        sort2(array, sub + 1 + right, end)
    }

    private fun reload(n: Int, t: Int) {
        sequenceView.n = n
        sequenceView.t = t
        mainHandler.post {
            sequenceView.invalidate()
        }
        Thread.sleep(10)
    }

    private fun swap(array: IntArray, x: Int, y: Int) {
        var temp = array[x]
        array[x] = array[y]
        array[y] = temp
    }
}