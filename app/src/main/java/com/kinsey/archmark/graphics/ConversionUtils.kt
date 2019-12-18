package com.kinsey.archmark.graphics

import androidx.arch.core.util.Function
import com.kinsey.archmark.model.TargetFace
import kotlin.math.*

object ConversionUtils {
    fun pixelToCm(x: Float, y: Float, targetView: TargetView, targetFace: TargetFace): Pair<Float, Float> {
        val xCm = getRelativeCmCoordinate(x, targetView.leftPadding, targetView.paddedWidth, targetFace.diameter)
        val yCm = getRelativeCmCoordinate(y, targetView.topPadding, targetView.paddedHeight, targetFace.diameter)
        return Pair(xCm, yCm)
    }

    private fun getRelativeCmCoordinate(coord: Float, padding: Int, paddedDimension: Int, diameter: Float): Float {
        return ((coord - padding)/paddedDimension)*diameter
    }

    fun cmCoordinatesToPolar(x: Float, y: Float, targetFace: TargetFace): Pair<Float, Float> {
        //Target radius offsets coordinates by radius in x and y directions, that's why the functions need them
        val targetRadius = targetFace.diameter/2

        val radius = getPolarRadius(targetRadius, x, y)
        var angle = getPolarAngle(targetRadius, x, y)

        //Account for CAST rule
        if ((targetRadius - y) < 0) {
            angle += PI.toFloat()
        }
        return Pair(angle, radius)
    }

    private fun getPolarRadius(targetRadius: Float, relX: Float, relY: Float): Float {
        return sqrt((targetRadius - relX).pow(2f) + (targetRadius - relY).pow(2f))
    }

    private fun getPolarAngle(targetRadius: Float, relX: Float, relY: Float): Float {
        return atan((targetRadius-relX)/(targetRadius - relY))
    }

    fun cmPolarToCoordinates(angle: Float, radius: Float, targetFace: TargetFace): Pair<Float, Float> {
        fun getCoordinate(trig: (Float) -> Float): Float {
            return ((targetFace.diameter/2)- trig(angle)*radius)
        }
        val x = getCoordinate(::sin)
        val y = getCoordinate(::cos)
        return Pair(x, y)
    }

    fun cmToPixel(x: Float, y: Float, targetView: TargetView, targetFace: TargetFace): Pair<Float, Float> {
        val xPx = getAbsolutePxCoordinate(x, targetFace.diameter, targetView.paddedHeight, targetView.leftPadding)
        val yPx = getAbsolutePxCoordinate(y, targetFace.diameter, targetView.paddedWidth, targetView.rightPadding)

        return Pair(xPx, yPx)

    }

    private fun getAbsolutePxCoordinate(coord: Float, diameter: Float, paddedDimension: Int, padding: Int): Float {
        return ((coord /diameter)*paddedDimension)+padding
    }
}