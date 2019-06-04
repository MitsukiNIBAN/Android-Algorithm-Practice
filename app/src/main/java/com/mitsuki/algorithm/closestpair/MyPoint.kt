package com.mitsuki.algorithm.closestpair

import android.graphics.Point
import com.mitsuki.algorithm.helper.SerialNumber

class MyPoint : SerialNumber, Point {

    override fun <MyPoint> number(t: MyPoint): Int {
        return x
    }

    constructor() : super()

    constructor(x: Int, y: Int) : super(x, y)

    var sortStandard = false

}