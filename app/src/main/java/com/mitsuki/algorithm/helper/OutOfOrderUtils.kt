package com.mitsuki.algorithm.helper

import java.util.*

object OutOfOrderUtils {
    //从归并排序改过来的,效果较好
    fun upset(array: IntArray, start: Int, end: Int, random: Random) {
        if (end < start) return

        var count = end - start + 1

        if (count < 3) {
            //治
            if (random.nextBoolean()) {
                if (array[start] > array[end]) {
                    var temp = array[end]
                    array[end] = array[start]
                    array[start] = temp
                }
            }
        } else {
            //分
            upset(array, start, start + count / 2 - 1, random)
            upset(array, start + count / 2, end, random)
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

                if (random.nextBoolean()) {
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
}