package com.kinsey.archmark.graphics

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.kinsey.archmark.model.Arrow

class ArrowMarker(val arrow: Arrow, var targetView: TargetView) {

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
        val cmCoords = ConversionUtils.cmPolarToCoordinates(arrow.angle, arrow.distance, targetView.targetFace)
        val markerCoords = ConversionUtils.cmToPixel(cmCoords.first, cmCoords.second, targetView, targetView.targetFace)
        canvas.drawCircle(markerCoords.first, markerCoords.second, this.outerRadius, this.outer)
        canvas.drawCircle(markerCoords.first, markerCoords.second, this.innerRadius, this.inner)

    }

}