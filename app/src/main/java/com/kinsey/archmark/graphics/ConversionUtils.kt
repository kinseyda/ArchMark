package com.kinsey.archmark.graphics

import com.kinsey.archmark.model.TargetFace
import kotlin.math.*

object ConversionUtils {

    /**
     * Converts a given pair of x-y pixel coordinates to coordinates for a [TargetFace].
     *
     * The calculated coordinates will be in cm units and have an origin in the top left corner of the [TargetFace].
     * If a square were fit as tightly as possible to [targetFace], then its top left corner would be the origin.
     * Importantly, this means [targetView]'s padding is removed.
     *
     * @param x the x coordinate (in pixels) of the coordinates to convert.
     * @param y the y coordinate (in pixels) of the coordinates to convert.
     * @param targetView the [TargetView] to convert pixel coordinates from.
     * @param targetFace the [TargetFace] to convert coordinates relative to.
     * @return a [Pair] containing the converted x and y coordinates.
     */
    fun pixelToCm(x: Float, y: Float, targetView: TargetView, targetFace: TargetFace): Pair<Float, Float> {
        val dimToUse = min(targetView.height - (targetView.padding*2), targetView.width - (targetView.padding*2))
        val xCm = getRelativeCmCoordinate(x, targetView.padding, dimToUse, targetFace.diameter)
        val yCm = getRelativeCmCoordinate(y, targetView.padding, dimToUse, targetFace.diameter)
        return Pair(xCm, yCm)
    }

    private fun getRelativeCmCoordinate(coord: Float, padding: Int, paddedDimension: Int, diameter: Float): Float {
        return ((coord - padding)/paddedDimension)*diameter
    }

    /**
     * Converts cm coordinates relative to the top left of a targetface to polar coordinates.
     *
     * @param x the x coordinate (in cm) to convert to polar form.
     * @param y the y coordinate (in cm) to convert to polar form.
     * @param targetFace the [TargetFace] to convert coordinates relative to.
     * @return a [Pair] containing first the angle (smallest amount of radians from north) then radius components of the polar form.
     */
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

    /**
     * Converts the polar coordinates of an [com.kinsey.archmark.model.Arrow] or otherwise to x-y coordinates based on the top left corner of a given [TargetFace].
     *
     * Both inputs and outputs will be in cm units.
     *
     * @param angle the angle from north in radians.
     * @param radius the distance to the center of [targetFace].
     * @param targetFace the [TargetFace] to base the coordinate system on.
     * @return a [Pair] containing the converted x and y coordinates, based on the top left corner of [targetFace].
     */
    fun cmPolarToCoordinates(angle: Float, radius: Float, targetFace: TargetFace): Pair<Float, Float> {
        fun getCoordinate(trig: (Float) -> Float): Float {
            return ((targetFace.diameter/2)- trig(angle)*radius)
        }
        val x = getCoordinate(::sin)
        val y = getCoordinate(::cos)
        return Pair(x, y)
    }

    /**
     * Converts a given pair of x-y cm coordinates to coordinates for a [TargetView].
     *
     * @param x the x coordinate (in cm) of the coordinates to convert.
     * @param y the y coordinate (in cm) of the coordinates to convert.
     * @param targetView the [TargetView] to convert pixel coordinates to.
     * @param targetFace the [TargetFace] to convert cm coordinates from.
     * @return a [Pair] containing the converted x and y coordinates.
     */
    fun cmToPixel(x: Float, y: Float, targetView: TargetView, targetFace: TargetFace): Pair<Float, Float> {
        val dimToUse = min(targetView.height - (targetView.padding*2), targetView.width - (targetView.padding*2))
        val xPx = getAbsolutePxCoordinate(x, targetFace.diameter, dimToUse, targetView.padding)
        val yPx = getAbsolutePxCoordinate(y, targetFace.diameter, dimToUse, targetView.padding)

        return Pair(xPx, yPx)

    }

    private fun getAbsolutePxCoordinate(coord: Float, diameter: Float, paddedDimension: Int, padding: Int): Float {
        return ((coord /diameter)*paddedDimension)+padding
    }
}