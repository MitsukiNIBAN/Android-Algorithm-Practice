package com.mitsuki.algorithm.fractal

import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector

abstract class MyGestureListener : GestureDetector.OnGestureListener, ScaleGestureDetector.OnScaleGestureListener {
    override fun onShowPress(e: MotionEvent?) {
    }

    override fun onSingleTapUp(e: MotionEvent?) = false

    override fun onDown(e: MotionEvent?) = true

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float) = false

    override fun onLongPress(e: MotionEvent?) {
    }

    override fun onScaleBegin(detector: ScaleGestureDetector?) = true

    override fun onScaleEnd(detector: ScaleGestureDetector?) {
    }

}