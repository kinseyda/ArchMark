package com.kinsey.archmark.Graphics

import com.kinsey.archmark.Model.TargetFace
import kotlin.math.*

fun pixelToCm(x: Float, y: Float, targetView: TargetView, targetFace: TargetFace): Pair<Float, Float> {
    return Pair(((x.toFloat()/targetView.width)*targetFace.diameter), ((y.toFloat()/targetView.height)*targetFace.diameter))
}

fun cmCoordinatesToPolar(x: Float, y: Float, targetFace: TargetFace): Pair<Float, Float> {
    val radius = sqrt(((targetFace.diameter / 2) - x).toDouble().pow(2.0) + ((targetFace.diameter / 2) - y).toDouble().pow(2.0)).toFloat()
    val angle = atan(((targetFace.diameter/2 - x)/((targetFace.diameter/2) - y)))
    return Pair(angle, radius)
}

fun cmPolarToCoordinates(angle: Float, radius: Float, targetFace: TargetFace): Pair<Float, Float> {
    val x = (sin(angle)*radius-(targetFace.diameter/2))*-1
    val y = (cos(angle)*radius-(targetFace.diameter/2))*-1
    return Pair(x, y)
}

fun cmToPixel(x: Float, y: Float, targetView: TargetView, targetFace: TargetFace): Pair<Float, Float> {
    return Pair(((x /targetFace.diameter)*targetView.width), ((y /targetFace.diameter)*targetView.height))

}