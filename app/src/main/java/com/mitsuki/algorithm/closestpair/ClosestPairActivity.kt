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
import java.util.*

class ClosestPairActivity : AppCompatActivity() {

    val MAX = 100
    val SIZE = 20
    val random = Random()
    var pointSet: MutableList<MyPoint> = ArrayList(SIZE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_closest_pair)
        title = "最近点对"

        var tempXArray = IntArray(MAX) { it.inc() }
        var tempYArray = IntArray(MAX) { it.inc() }

        OutOfOrderUtils.upset(tempXArray, 0, MAX - 1, random)
        OutOfOrderUtils.upset(tempYArray, 0, MAX - 1, random)

        for (i in 0 until SIZE) {
            pointSet.add(MyPoint(tempXArray[i], tempYArray[i]))
        }

        SortUtils.quickSort(pointSet, 0, SIZE - 1) { it.x.toDouble() }

        cpair(pointSet)
    }


    private fun cpair(array: MutableList<MyPoint>): Double {
        if (array.size < 2) return Double.MAX_VALUE

        var s1 = array.subList(0, array.size / 2)
        var s2 = array.subList(array.size / 2, array.size)
        var d1 = cpair(s1)
        var d2 = cpair(s2)

        var dm = Math.min(d1, d2)

        return 0.0
    }

}
