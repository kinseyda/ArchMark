package com.kinsey.archmark.graphics

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.kinsey.archmark.model.Ring
import com.kinsey.archmark.model.TargetFace

object RingDrawer {

    private val paint = Paint().apply { style = Paint.Style.FILL }
    private var outline: Int = Color.BLACK
    private var outlineWidth: Float = 2f
    private var colorMap: MutableMap<Int, Int> = mutableMapOf(

        1 to Color.WHITE
        2 to Color.WHITE
        3 to Color.BLACK
        4 to Color.BLACK
        5 to Color.BLUE
        6 to Color.BLUE
        7 to Color.RED
        8 to Color.RED
        9 to Color.YELLOW
        10 to Color.YELLOW
        11 to Color.YELLOW

    )

    fun drawRing(canvas: Canvas, ring: Ring, targetFace: TargetFace,centerX: Int, centerY: Int, viewRadius: Int) {
        val ringRadius = viewRadius*(ring.radius/(targetFace.diameter/2))

        paint.color = outline
        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), ringRadius + outlineWidth, paint)

        RingDrawer.colorMap[ring.score.toInt()]?.let { 
            paint.color = c
         }
        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), ringRadius, paint)

    }
}