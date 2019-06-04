package com.mitsuki.algorithm.closestpair

import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mitsuki.algorithm.R
import com.mitsuki.algorithm.helper.MedianUtils
import com.mitsuki.algorithm.helper.OutOfOrderUtils
import com.mitsuki.algorithm.helper.SerialNumber
import com.mitsuki.algorithm.helper.SortUtils
import kotlinx.android.synthetic.main.activity_closest_pair.*
import java.lang.RuntimeException
import java.util.*
import kotlin.collections.ArrayList

class ClosestPairActivity : AppCompatActivity() {

    val MAX = 100
    val SIZE = 20
    val random = Random()
    var pointSet: MutableList<Point> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_closest_pair)
        title = "最近点对"

        var tempXArray = IntArray(MAX) { it.inc() }
        var tempYArray = IntArray(MAX) { it.inc() }

        OutOfOrderUtils.upset(tempXArray, 0, MAX - 1, random)
        OutOfOrderUtils.upset(tempYArray, 0, MAX - 1, random)

        for (i in 0 until SIZE) {
            pointSet.add(Point(tempXArray[i], tempYArray[i]))
        }

        SortUtils.quickSort(pointSet, 0, SIZE - 1) { it.x.toDouble() }

        cpair(pointSet, 0, pointSet.size - 1)
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

        var d1 = cpair(array, start, count / 2)
        var d2 = cpair(array, count / 2, end)

        var best = if (d1.dist > d2.dist) d2 else d1


        var temp = ArrayList<Point>()

        for (position in start..end) {
            if (Math.abs(array[count / 2].x - array[position].x) < best.dist) {
                temp.add(array[position])
            }
        }

        SortUtils.quickSort(temp, 0, temp.size) { it.y.toDouble() }

        for (i in 0 until temp.size) {
            var j = i + 1
            while (++j < temp.size && temp[j].y - temp[i].y < best.dist) {
                var dp = Pair.dist(temp[i], temp[j])
                if (dp < best.dist) {
                    best = Pair(temp[i], temp[j], dp)
                }
            }
        }

        return best
    }

}
