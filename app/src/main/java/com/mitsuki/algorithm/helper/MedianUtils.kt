package com.mitsuki.algorithm.helper

/**
 * 获取中位数
 * 参考：https://blog.csdn.net/liufeng_king/article/details/8480430
 */
object MedianUtils {
    fun find(array: IntArray): Int {

        if (array.isEmpty()) return -1

        if (array.size < 5) {
            insertionSort(array)
            return array[array.size / 2]
        }

        var count = array.size / 5 //5个一组分组
        var median = IntArray(count)
        var temp = IntArray(5)
        for (i in 0 until count) {
            array.copyInto(temp, 0, i * 5, (i + 1) * 5)
            insertionSort(temp)
            median[i] = temp[2]
        }

        return find(median)
    }

    //插入排序
    private fun insertionSort(array: IntArray) {
        for (j in 1 until 5) {
            var key = array[j]
            var k = j - 1
            while (k >= 0 && array[k] > key) {
                array[k + 1] = array[k]
                k--
            }
            array[k + 1] = key
        }
    }

}