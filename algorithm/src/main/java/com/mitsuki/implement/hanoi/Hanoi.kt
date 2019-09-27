package com.mitsuki.implement.hanoi

import com.mitsuki.implement.OnBitmapCallback

object Hanoi {

    init {
        System.loadLibrary("native-lib")
    }

    external fun init(number: Int): FloatArray

    external fun move(currency: FloatArray, callback: OnBitmapCallback)

//    fun move(hanoi: FloatArray) {
//        val count = hanoi.size
//        val hanoiTarget = FloatArray(count) { -1f }
//        val hanoiAssist = FloatArray(count) { -1f }
//        move(count, hanoi, hanoiTarget, hanoiAssist)
//    }

}
