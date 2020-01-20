package com.kinsey.archmark.graphics.target

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.kinsey.archmark.model.Ring
import com.kinsey.archmark.model.TargetFace

object XDrawer {
    private val outlineColor = Color.BLACK
    private val size = 8f
    //TODO make this be measured in cm, scale with target so as to never leave X ring
    private val width = 2f
    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        color = outlineColor
        strokeWidth = width
    }

    fun drawX(canvas: Canvas, centerX: Int, centerY: Int) {
        canvas.drawLine(centerX.toFloat(), centerY.toFloat(), centerX.toFloat()+size, centerY.toFloat(), paint)
        canvas.drawLine(centerX.toFloat(), centerY.toFloat(), centerX.toFloat(), centerY.toFloat()+size, paint)
        canvas.drawLine(centerX.toFloat(), centerY.toFloat(), centerX.toFloat()-size, centerY.toFloat(), paint)
        canvas.drawLine(centerX.toFloat(), centerY.toFloat(), centerX.toFloat(), centerY.toFloat()-size, paint)
    }

}