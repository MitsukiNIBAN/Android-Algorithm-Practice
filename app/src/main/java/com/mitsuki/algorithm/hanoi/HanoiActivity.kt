package com.mitsuki.algorithm.hanoi

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.appcompat.app.AppCompatActivity
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.Toast
import com.mitsuki.implement.OnBitmapCallback
import com.mitsuki.implement.hanoi.Hanoi
import kotlinx.android.synthetic.main.activity_hanoi.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.*

class HanoiActivity : AppCompatActivity() {

    private val hanoiA: MutableList<Dish> = ArrayList() //塔A
    private val hanoiB: MutableList<Dish> = ArrayList() //塔B
    private val hanoiC: MutableList<Dish> = ArrayList() //塔C
    private var thread: HandlerThread = HandlerThread("hanoi")
    private lateinit var handler: Handler
    lateinit var mainHandler: Handler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mitsuki.algorithm.R.layout.activity_hanoi)

        title = "Hanoi"


        val df = Hanoi.init(3)

        Hanoi.move(df, object : OnBitmapCallback {
            override fun callback(bitmap: Bitmap) {
                Toast.makeText(this@HanoiActivity, "sasdfas", Toast.LENGTH_SHORT).show()
            }
        })


        thread.start()
        handler = Handler(thread.looper)
        mainHandler = Handler(mainLooper)

        log.movementMethod = ScrollingMovementMethod.getInstance()
        log.text = ""

        start.isEnabled = false

        pileA.hanoiList = hanoiA
        pileB.hanoiList = hanoiB
        pileC.hanoiList = hanoiC

        reset.setOnClickListener {
            hanoiA.clear()
            hanoiB.clear()
            hanoiC.clear()

            log.text = ""

            if (!TextUtils.isEmpty(number.text.toString())) {
                val myRandom = Random()
                for (i in 1..Integer.parseInt(number.text.toString())) {
                    hanoiA.add(
                        Dish(
                            i,
                            Color.rgb(
                                myRandom.nextInt(255),
                                myRandom.nextInt(255),
                                myRandom.nextInt(255)
                            )
                        )
                    )
                }
                start.hint = number.text.toString()
                start.isEnabled = true
            } else {
                start.isEnabled = false
            }

            pileA.invalidate()
            pileB.invalidate()
            pileC.invalidate()
        }

        start.setOnClickListener {
            //开始计算
            handler.post {
                hanoi(Integer.parseInt(start.hint.toString()), hanoiA, hanoiC, hanoiB)
            }
            start.isEnabled = false
        }

    }

    override fun onDestroy() {
        thread.quit()
        super.onDestroy()
    }


    private fun hanoi(
        n: Int,
        currency: MutableList<Dish>,
        target: MutableList<Dish>,
        assist: MutableList<Dish>
    ) {
        if (n < 1)
            return
        //递归A->B(C)
        hanoi(n - 1, currency, assist, target)

        //A->C(B)
        var tag = currency.last()
        target.add(tag)
        currency.remove(tag)
        mainHandler.post {
            //            log.append(getLog(tag.color, getName(currency.hashCode()), getName(target.hashCode())))
            pileA.invalidate()
            pileB.invalidate()
            pileC.invalidate()
        }
        Thread.sleep(500)

        //递归B->C(A)
        hanoi(n - 1, assist, target, currency)
    }

    private fun getName(code: Int): String {
        return when (code) {
            hanoiA.hashCode() -> "A"
            hanoiB.hashCode() -> "B"
            hanoiC.hashCode() -> "C"
            else -> ""
        }
    }

    private fun getLog(color: Int, cur: String, tar: String): SpannableString {
        val spannable = SpannableString("Move ■ from $cur to $tar\n")
        spannable.setSpan(ForegroundColorSpan(color), 5, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable
    }
}
