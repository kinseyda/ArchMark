package com.kinsey.archmark.graphics.target

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.kinsey.archmark.model.Ring
import com.kinsey.archmark.model.TargetFace

object RingDrawer {

    private val paint = Paint().apply { style = Paint.Style.FILL }
    private var outlineMap: MutableMap<Int, Int> = mutableMapOf(

        1 to Color.BLACK,
        2 to Color.BLACK,
        3 to Color.BLACK,
        4 to Color.WHITE,
        5 to Color.BLACK,
        6 to Color.BLACK,
        7 to Color.BLACK,
        8 to Color.BLACK,
        9 to Color.BLACK,
        10 to Color.BLACK,
        11 to Color.BLACK

    )
    private var outlineWidth: Float = 2f
    private var colorMap: MutableMap<Int, Int> = mutableMapOf(

        1 to Color.WHITE,
        2 to Color.WHITE,
        3 to Color.BLACK,
        4 to Color.BLACK,
        5 to Color.BLUE,
        6 to Color.BLUE,
        7 to Color.RED,
        8 to Color.RED,
        9 to Color.YELLOW,
        10 to Color.YELLOW,
        11 to Color.YELLOW

    )

    fun drawRing(canvas: Canvas, ring: Ring, targetFace: TargetFace,centerX: Int, centerY: Int, viewRadius: Int) {
        val ringRadius = viewRadius*(ring.radius/(targetFace.diameter/2))

        outlineMap[ring.score.toInt()]?.let {
            paint.color = it
        }
        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), ringRadius + outlineWidth,
            paint
        )

        colorMap[ring.score.toInt()]?.let {
            paint.color = it
         }
        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), ringRadius,
            paint
        )

    }
}