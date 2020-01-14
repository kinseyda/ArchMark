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

class ArrowMarkerFancy(val arrow: Arrow, var targetView: TargetView) {

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
        //Get coords
        val cmCoords = ConversionUtils.cmPolarToCoordinates(
            arrow.angle,
            arrow.distance,
            targetView.mainActivity.getCard().targetFace
        )
        val markerCoords = ConversionUtils.cmToPixel(
            cmCoords.first,
            cmCoords.second,
            targetView,
            targetView.mainActivity.getCard().targetFace
        )

        //Draw index
        canvas.drawLine(markerCoords.first, markerCoords.second, markerCoords.first, markerCoords.second - vaneLength, indexVane)

        //Draw hen
        canvas.drawLine(markerCoords.first, markerCoords.second, markerCoords.first + cos(PI / 5).toFloat()*vaneLength, markerCoords.second + sin(PI / 5).toFloat()*vaneLength , henVane)
        canvas.drawLine(markerCoords.first, markerCoords.second, markerCoords.first - cos(PI / 5).toFloat()*vaneLength, markerCoords.second + sin(PI / 5).toFloat()*vaneLength , henVane)

        //Draw shaft
        canvas.drawCircle(markerCoords.first, markerCoords.second, this.shaftRadius, this.shaft)

        //Draw nock
        canvas.drawCircle(markerCoords.first, markerCoords.second, this.nockRadius, this.nock)

    }

}