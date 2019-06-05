package com.mitsuki.algorithm.closestpair

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mitsuki.algorithm.R
import com.mitsuki.algorithm.helper.SortUtils
import com.mitsuki.algorithm.helper.TaskThreadPoolExecutor
import kotlinx.android.synthetic.main.activity_binary_search.findBtn
import kotlinx.android.synthetic.main.activity_closest_pair.*
import kotlinx.android.synthetic.main.activity_fractal.initBtn
import java.lang.Exception
import java.lang.RuntimeException
import java.util.*
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class ClosestPairActivity : AppCompatActivity() {

    val random = Random()
    var width = 0
    var height = 0
    var pointSet: MutableList<Point> = ArrayList()
    private lateinit var taskPool: TaskThreadPoolExecutor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_closest_pair)
        title = "最近点对"

        sketch.post {
            initBtn.isEnabled = true
            taskPool = TaskThreadPoolExecutor(1, 5, 500, TimeUnit.MILLISECONDS, LinkedBlockingQueue())
            width = sketch.width
            height = sketch.height
        }
        initBtn.setOnClickListener {
            taskPool.execute {
                try {
                    pointSet.clear()
                    for (i in 0 until Integer.parseInt(pointCount.text.toString())) {
                        var p = Point(random.nextInt(width), random.nextInt(height))
                        pointSet.add(p)
                    }
                    SortUtils.quickSort(pointSet, 0, pointSet.size - 1) { it.x.toDouble() }
                    sketch.pointSet = pointSet
                    sketch.start = 0
                    sketch.end = -1
                    sketch.best = null
                    runOnUiThread {
                        findBtn.isEnabled = true
                        sketch.invalidate()
                    }
                } catch (e: Exception) {

                }

            }
        }

        findBtn.setOnClickListener {
            findBtn.isEnabled = false
            taskPool.execute {
                try {
                    var p = cpairForShow(pointSet, 0, pointSet.size - 1)
                    sketch.p1 = null
                    sketch.p2 = null
                    sketch.best = p
                    sketch.temp = null
                    sketch.start = -1
                    sketch.end = -1
                    updateView()

                } catch (e: Exception) {

                }
            }
        }
    }

    private fun cpair(array: MutableList<Point>, start: Int, end: Int): Pair {
        val count = end - start + 1
        if (count < 2) throw RuntimeException("params error")
        if (count == 2) {
            return Pair(array[start], array[end], Pair.dist(array[start], array[end]))
        }
        if (count == 3) {
            var d1 = Pair.dist(array[start], array[start + 1])
            var d2 = Pair.dist(array[start + 1], array[end])
            var d3 = Pair.dist(array[start], array[end])
            return when {
                d1 <= d2 && d1 <= d3 -> Pair(array[start], array[start + 1], d1)
                d2 <= d3 -> Pair(array[start + 1], array[end], d2)
                else -> Pair(array[start], array[end], d3)
            }
        }

        var d1 = cpair(array, start, start + count / 2 - 1)
        var d2 = cpair(array, start + count / 2, end)

        var best = if (d1.dist > d2.dist) d2 else d1

        var temp = ArrayList<Point>()

        for (position in start..end) {
            if (Math.abs(array[start + count / 2 - 1].x - array[position].x) < best.dist) {
                temp.add(array[position])
            }
        }

        SortUtils.quickSort(temp, 0, temp.size - 1) { it.y.toDouble() }

        for (i in 0 until temp.size) {
            var j = i + 1
            while (j < temp.size && temp[j].y - temp[i].y < best.dist) {
                var dp = Pair.dist(temp[i], temp[j])
                if (dp < best.dist) {
                    best = Pair(temp[i], temp[j], dp)
                }
                j++
            }
        }

        return best
    }

    private fun cpairForShow(array: MutableList<Point>, start: Int, end: Int): Pair {

        sketch.start = start
        sketch.end = end
        updateView()

        val count = end - start + 1
        if (count < 2) throw RuntimeException("params error")
        if (count == 2) {

            sketch.p1 = array[start]
            sketch.p2 = array[end]
            updateView()

            return Pair(array[start], array[end], Pair.dist(array[start], array[end]))
        }
        if (count == 3) {

            sketch.p1 = array[start]
            sketch.p2 = array[start + 1]
            updateView()

            var d1 = Pair.dist(array[start], array[start + 1])

            sketch.p1 = array[start + 1]
            sketch.p2 = array[end]
            updateView()

            var d2 = Pair.dist(array[start + 1], array[end])

            sketch.p1 = array[start]
            sketch.p2 = array[end]
            updateView()

            var d3 = Pair.dist(array[start], array[end])
            return when {
                d1 <= d2 && d1 <= d3 -> Pair(array[start], array[start + 1], d1)
                d2 <= d3 -> Pair(array[start + 1], array[end], d2)
                else -> Pair(array[start], array[end], d3)
            }
        }

        var d1 = cpairForShow(array, start, start + count / 2 - 1)

        sketch.p1 = null
        sketch.p2 = null
        updateView()

        var d2 = cpairForShow(array, start + count / 2, end)

        sketch.p1 = null
        sketch.p2 = null

        var best = if (d1.dist > d2.dist) d2 else d1

        sketch.best = best
        updateView()

        var temp = ArrayList<Point>()

        for (position in start..end) {
            if (Math.abs(array[start + count / 2 - 1].x - array[position].x) < best.dist) {
                temp.add(array[position])
            }
        }
        sketch.temp = temp
        updateView()

        SortUtils.quickSort(temp, 0, temp.size - 1) { it.y.toDouble() }

        for (i in 0 until temp.size) {
            var j = i + 1
            while (j < temp.size && temp[j].y - temp[i].y < best.dist) {

                sketch.p1 = temp[i]
                sketch.p2 = temp[j]
                updateView()

                var dp = Pair.dist(temp[i], temp[j])
                if (dp < best.dist) {
                    best = Pair(temp[i], temp[j], dp)
                }
                j++
            }
        }

        sketch.p1 = null
        sketch.p2 = null
        sketch.best = best
        sketch.temp = null
        updateView()

        return best
    }

    private fun updateView() {
        runOnUiThread {
            sketch.invalidate()
        }
        Thread.sleep(200)
    }
}
