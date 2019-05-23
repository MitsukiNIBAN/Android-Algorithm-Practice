package com.mitsuki.algorithm.mergesort

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import com.mitsuki.algorithm.R
import kotlinx.android.synthetic.main.activity_binary_search.*
import kotlinx.android.synthetic.main.activity_binary_search.initBtn
import kotlinx.android.synthetic.main.activity_merge_sort.*
import kotlinx.android.synthetic.main.activity_merge_sort.view.*
import java.util.*

class MergeSortActivity : AppCompatActivity() {

    private val COUNT = 200
    private var thread: HandlerThread = HandlerThread("MergeSort")
    private lateinit var handler: Handler
    private lateinit var mainHandler: Handler
    private var random = Random()
    var sequence: IntArray = IntArray(COUNT)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_merge_sort)

        thread.start()
        handler = Handler(thread.looper)
        mainHandler = Handler(mainLooper)

        initBtn.setOnClickListener {
            handler.post {
                for (i in 1..COUNT) {
                    sequence[i - 1] = i
                }
                upset(sequence, 0, COUNT - 1)
                sequenceView.sequence = sequence
                sequenceView.s = 0
                sequenceView.e = -1
                mainHandler.post {
                    sequenceView.invalidate()
                }
            }
        }

        sortBtn.setOnClickListener {
            handler.post {
                sort(sequence, 0, COUNT - 1)
            }
        }

    }

    //打乱方法1
    private fun randomSwap(array: IntArray) {
        for (i in 0 until array.size) {
            var sp = random.nextInt(array.size - i)
            if (sp != array.size - 1) {
                var temp = array[array.size - 1]
                array[array.size - 1] = array[sp]
                array[sp] = temp
            }
        }

        for (i in 0 until array.size) {
            var sp = (array.size - 1) - random.nextInt(array.size - i)
            if (sp != 0) {
                var temp = array[0]
                array[0] = array[sp]
                array[sp] = temp
            }
        }
    }

    //归并排序
    private fun sort(array: IntArray, start: Int, end: Int) {
        if (end < start) return

        sequenceView.s = start
        sequenceView.e = end

        var count = end - start + 1

        if (count < 3) {
            //治
            if (count == 2) {
                if (array[start] > array[end]) {
                    var temp = array[end]
                    array[end] = array[start]
                    array[start] = temp
                    reload()
                }
            }
        } else {
            //分
            sort(array, start, start + count / 2 - 1)
            sequenceView.s = start
            sequenceView.e = end
            sort(array, start + count / 2, end)
            sequenceView.s = start
            sequenceView.e = end
            //合
            //合在新数列，最后拷贝至原数列
//            var temp = IntArray(count)
//            var s1 = start
//            var s2 = start + count / 2
//            for (i in 0 until count) {
//                if (s1 > start + count / 2 - 1) {
//                    temp[i] = array[s2]
//                    s2++
//                    continue
//                }
//
//                if (s2 > end) {
//                    temp[i] = array[s1]
//                    s1++
//                    continue
//                }
//
//                if (array[s1] > array[s2]) {
//                    temp[i] = array[s2]
//                    s2++
//                } else {
//                    temp[i] = array[s1]
//                    s1++
//                }
//            }
//            temp.copyInto(array, start)
//            mainHandler.post {
//                sequenceView.invalidate()
//            }
//            Thread.sleep(500)
            //将一半拷贝至新数列，直接在原数列中排序
            var temp1 = IntArray(count / 2)
            var temp2 = IntArray(count - count / 2)
            array.copyInto(temp1, 0, start, start + count / 2)
            array.copyInto(temp2, 0, start + count / 2, end + 1)
            var s1 = 0
            var s2 = 0

            for (i in 0 until count) {
                if (s1 >= temp1.size) {
                    array[i + start] = temp2[s2]
                    s2++
                    reload()
                    continue
                }

                if (s2 >= temp2.size) {
                    array[i + start] = temp1[s1]
                    s1++
                    reload()
                    continue
                }

                if (temp1[s1] > temp2[s2]) {
                    array[i + start] = temp2[s2]
                    s2++
                } else {
                    array[i + start] = temp1[s1]
                    s1++
                }
                reload()
            }
        }
    }

    //打乱方法2,从归并改过来的,效果比方法1好
    private fun upset(array: IntArray, start: Int, end: Int) {
        if (end < start) return

        var count = end - start + 1

        if (count < 3) {
            //治
            if (random.nextInt(2) == 0) {
                if (array[start] > array[end]) {
                    var temp = array[end]
                    array[end] = array[start]
                    array[start] = temp
                }
            }
        } else {
            //分
            upset(array, start, start + count / 2 - 1)
            upset(array, start + count / 2, end)
            //合
            var temp = IntArray(count)
            var s1 = start
            var s2 = start + count / 2
            for (i in 0 until count) {
                if (s1 > start + count / 2 - 1) {
                    temp[i] = array[s2]
                    s2++
                    continue
                }

                if (s2 > end) {
                    temp[i] = array[s1]
                    s1++
                    continue
                }

                if (random.nextInt(2) == 0) {
                    temp[i] = array[s2]
                    s2++
                } else {
                    temp[i] = array[s1]
                    s1++
                }
            }
            temp.copyInto(array, start)
        }
    }

    private fun reload() {
        mainHandler.post {
            sequenceView.invalidate()
        }
        Thread.sleep(100)
    }
}
