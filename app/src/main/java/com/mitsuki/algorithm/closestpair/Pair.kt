package com.mitsuki.algorithm.closestpair

import android.graphics.Point

class Pair(a: Point, b: Point, d: Double) {
    var p1 = a
    var p2 = b
    var dist = d

    companion object {
        fun dist(a: Point, b: Point) = Math.sqrt(((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y)).toDouble())
    }

}