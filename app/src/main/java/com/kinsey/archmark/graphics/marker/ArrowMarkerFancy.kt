package com.kinsey.archmark.graphics.marker

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.kinsey.archmark.graphics.target.ConversionUtils
import com.kinsey.archmark.graphics.target.TargetView
import com.kinsey.archmark.model.Arrow
import java.lang.Math.PI
import kotlin.math.cos
import kotlin.math.sin

class ArrowMarkerFancy(var x: Float, var y: Float, var targetView: TargetView) {

    private val shaft = Paint().apply{
        style = Paint.Style.FILL
        color = Color.BLACK
    }
    private val shaftRadius = 20f

    private val nock = Paint().apply{
        style = Paint.Style.FILL
        color = Color.WHITE
    }
    private val nockRadius = 15f

    private val indexVane = Paint().apply{
        style = Paint.Style.STROKE
        strokeWidth = 5f
        color = Color.WHITE
    }
    private val henVane = Paint().apply{
        style = Paint.Style.STROKE
        strokeWidth = 5f
        color = Color.BLUE
    }
    private val vaneLength = 50f


    fun drawMarker(canvas: Canvas) {

        //Draw index
        canvas.drawLine(x, y, x, y - vaneLength, indexVane)

        //Draw hen
        canvas.drawLine(x, y, x + cos(PI / 5).toFloat()*vaneLength, y + sin(PI / 5).toFloat()*vaneLength , henVane)
        canvas.drawLine(x, y, x - cos(PI / 5).toFloat()*vaneLength, y + sin(PI / 5).toFloat()*vaneLength , henVane)

        //Draw shaft
        canvas.drawCircle(x, y, this.shaftRadius, this.shaft)

        //Draw nock
        canvas.drawCircle(x, y, this.nockRadius, this.nock)

    }

}