package com.kinsey.archmark.Graphics

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.kinsey.archmark.Model.Ring

class RingDrawer {
    private val paint =
        Paint().apply {
            color = Color.BLUE
            style = Paint.Style.FILL
        }

    fun drawRing(canvas: Canvas, ring: Ring, centerX: Int, centerY: Int, viewRadius: Int) {
        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), viewRadius.toFloat(), paint)
    }
}