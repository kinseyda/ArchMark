package com.kinsey.archmark.Graphics

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.kinsey.archmark.Model.Arrow

class ArrowMarker(val arrow: Arrow, var targetView: TargetView) {

    private val paint = Paint().apply{
        style = Paint.Style.FILL
        color = Color.BLACK
    }
    private val markerRadius = 5f

    fun drawMarker(canvas: Canvas) {
        val cmCoords = cmPolarToCoordinates(arrow.angle, arrow.distance, targetView.targetFace)
        val markerCoords = cmToPixel(cmCoords.first, cmCoords.second, targetView, targetView.targetFace)
        canvas.drawCircle(markerCoords.first, markerCoords.second, this.markerRadius, paint)
    }

}