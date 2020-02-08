package com.kinsey.archmark.graphics.marker

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.kinsey.archmark.graphics.target.ConversionUtils
import com.kinsey.archmark.graphics.target.TargetView
import com.kinsey.archmark.model.Arrow

class ArrowMarker(var x: Float, var y: Float, var targetView: TargetView) {

    private val outer = Paint().apply{
        style = Paint.Style.FILL
        color = Color.BLACK
    }
    private val outerRadius = 15f

    private val inner = Paint().apply{
        style = Paint.Style.FILL
        color = Color.WHITE
    }
    private val innerRadius = 12f

    fun drawMarker(canvas: Canvas) {
        canvas.drawCircle(x, y, this.outerRadius, this.outer)
        canvas.drawCircle(x, y, this.innerRadius, this.inner)
    }

}