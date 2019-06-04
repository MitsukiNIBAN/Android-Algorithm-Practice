package com.mitsuki.algorithm.helper

import java.util.Collections.swap

object SortUtils {
    //归并排序
    fun <T> mergeSort(array: MutableList<T>, start: Int, end: Int, standard: (T) -> Double) {
        if (end < start) return

        val count = end - start + 1

        if (count < 3) {
            //治
            if (count == 2) {
                if (standard(array[start]) > standard(array[end])) {
                    val temp = array[end]
                    array[end] = array[start]
                    array[start] = temp
                }
            }
        } else {
            //分
            mergeSort(array, start, start + count / 2 - 1, standard)
            mergeSort(array, start + count / 2, end, standard)
            //合
            //将一半拷贝至新数列，直接在原数列中排序
            val temp1 = ArrayList<T>(array.subList(start, start + count / 2))
            val temp2 = ArrayList<T>(array.subList(start + count / 2, end + 1))

            var s1 = 0
            var s2 = 0

            for (i in 0 until count) {
                if (s1 >= temp1.size) {
                    array[i + start] = temp2[s2]
                    s2++
                    continue
                }

                if (s2 >= temp2.size) {
                    array[i + start] = temp1[s1]
                    s1++
                    continue
                }

                if (standard(temp1[s1]) > standard(temp2[s2])) {
                    array[i + start] = temp2[s2]
                    s2++
                } else {
                    array[i + start] = temp1[s1]
                    s1++
                }
            }
        }
    }

    //插入排序
    fun <T> insertionSort(array: MutableList<T>, standard: (T) -> Double) {
        for (j in 1 until array.size) {
            var key = array[j]
            var k = j - 1
            while (k >= 0 && standard(array[k]) > standard(key)) {
                array[k + 1] = array[k]
                k--
            }
            array[k + 1] = key
        }
    }

    //快速排序优化(三数取中 + 聚集相等元素)
    fun <T> quickSort(array: MutableList<T>, start: Int, end: Int, standard: (T) -> Double) {
        var count = end - start + 1
        if (count < 2) return
        if (count < 3) {
            if (standard(array[start]) > standard(array[end]))
                swap(array, start, end)
            return
        }

        //三数取中确定枢轴
        if (standard(array[count / 2]) > standard(array[end])) {
            if (standard(array[count / 2]) <= standard(array[start])) {
                swap(array, start, count / 2)
            } else {
                if (standard(array[start]) < standard(array[end])) {
                    swap(array, start, end)
                }
            }
        } else {
            if (standard(array[count / 2]) >= standard(array[start])) {
                swap(array, start, count / 2)
            } else {
                if (standard(array[start]) > standard(array[end])) {
                    swap(array, start, end)
                }
            }
        }

        var sub = start
        var head = start + 1
        var tail = end
        var tag = true

        for (i in 1 until count) {
            if (tag) {
                if (standard(array[sub]) > standard(array[tail])) {
                    swap(array, sub, tail)
                    sub = tail
                    tag = false
                }
                tail--
            } else {
                if (standard(array[sub]) < standard(array[head])) {
                    swap(array, sub, head)
                    sub = head
                    tag = true
                }
                head++
            }
        }

        //聚集相等元素
        var left = 0
        var right = 0
//        for (i in start..end) {
//            if (i == sub) continue
//            if (i < sub) {
//                if (standard(array[sub - 1 - i + start]) == standard(array[sub])) {
//                    left++
//                    swap(array, sub - 1 - i + start, sub - left)
//                }
//            }
//            if (i > sub) {
//                if (standard(array[i]) == standard(array[sub])) {
//                    right++
//                    swap(array, i, sub + right)
//                }
//            }
//        }

        quickSort(array, start, sub - 1 - left, standard)
        quickSort(array, sub + 1 + right, end, standard)
    }

}