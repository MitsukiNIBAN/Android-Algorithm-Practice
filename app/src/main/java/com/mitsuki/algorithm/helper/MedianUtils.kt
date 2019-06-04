package com.mitsuki.algorithm.helper

import com.mitsuki.algorithm.helper.SortUtils.insertionSort

/**
 * 获取中位数
 * 参考：https://blog.csdn.net/liufeng_king/article/details/8480430
 */
object MedianUtils {

    fun <T : SerialNumber> find(array: MutableList<T>): SerialNumber {
        if (array.isEmpty()) return object : SerialNumber {
            override fun number(): Int = -1
        }

        if (array.size < 5) {
            insertionSort(array)
            return array[array.size / 2]
        }

        var count = array.size / 5 //5个一组分组
        var median = ArrayList<SerialNumber>()
        var temp = ArrayList<SerialNumber>()
        for (i in 0 until count) {
            temp.clear()
            temp.addAll(array.subList(i * 5, (i + 1) * 5))
            insertionSort(temp)
            median.add(temp[2])
        }

        return find(median)
    }



}