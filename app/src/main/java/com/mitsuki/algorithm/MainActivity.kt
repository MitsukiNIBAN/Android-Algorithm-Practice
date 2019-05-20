package com.mitsuki.algorithm

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mitsuki.algorithm.binarysearch.BinarySearchActivity
import com.mitsuki.algorithm.chinesering.ChineseRingActivity
import com.mitsuki.algorithm.hanoi.HanoiActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hanoi.setOnClickListener{
            startActivity(Intent(this, HanoiActivity::class.java))
        }
        chineseRing.setOnClickListener{
            startActivity(Intent(this, ChineseRingActivity::class.java))
        }
        binarySearch.setOnClickListener {
            startActivity(Intent(this, BinarySearchActivity::class.java))
        }
    }
}
