package com.kinsey.archmark.Graphics

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.kinsey.archmark.Model.Ring
import com.kinsey.archmark.Model.TargetFace

class RingDrawer {
    private val paint =
        Paint().apply {
            style = Paint.Style.FILL
        }
    private var outline: Int = Color.BLACK
    private var outlineWidth: Float = 2f
    private var colorMap: MutableMap<Int, Int> = mutableMapOf()
    init {
        colorMap[1] = Color.WHITE
        colorMap[2] = Color.WHITE
        colorMap[3] = Color.BLACK
        colorMap[4] = Color.BLACK
        colorMap[5] = Color.BLUE
        colorMap[6] = Color.BLUE
        colorMap[7] = Color.RED
        colorMap[8] = Color.RED
        colorMap[9] = Color.YELLOW
        colorMap[10] = Color.YELLOW
        colorMap[11] = Color.YELLOW
    }


    fun drawRing(canvas: Canvas, ring: Ring, targetFace: TargetFace,centerX: Int, centerY: Int, viewRadius: Int) {
        val ringRadius = viewRadius*(ring.radius/(targetFace.diameter/2))

        paint.apply {
            color = outline
        }
        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), ringRadius + outlineWidth, paint)

        val c = colorMap[ring.score.toInt()]
        c?.let {
            //Only null if score is not in dictionary
            paint.apply {
                color = c
            }
        }
        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), ringRadius, paint)

    }
}