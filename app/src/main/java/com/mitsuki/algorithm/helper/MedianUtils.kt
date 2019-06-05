package com.mitsuki.algorithm.helper

import com.mitsuki.algorithm.helper.SortUtils.insertionSort

/**
 * 获取中位数
 * 参考：https://blog.csdn.net/liufeng_king/article/details/8480430
 */
object MedianUtils {

    fun <T> find(array: MutableList<T>, standard: (T) -> Double): T? {
        if (array.isEmpty()) return null

        if (array.size < 5) {
            insertionSort(array, standard)
            return array[array.size / 2]
        }

        var count = array.size / 5 //5个一组分组
        var median = ArrayList<T>()
        var temp = ArrayList<T>()
        for (i in 0 until count) {
            temp.clear()
            temp.addAll(array.subList(i * 5, (i + 1) * 5))
            insertionSort(temp, standard)
            median.add(temp[2])
        }

        return find(median, standard)
    }


}