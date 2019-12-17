package com.kinsey.archmark.graphics

import com.kinsey.archmark.model.TargetFace
import kotlin.math.*

fun pixelToCm(x: Float, y: Float, targetView: TargetView, targetFace: TargetFace): Pair<Float, Float> {
    return Pair((((x - targetView.leftPadding)/targetView.paddedWidth)*targetFace.diameter), (((y - targetView.topPadding)/targetView.paddedHeight)*targetFace.diameter))
}

fun cmCoordinatesToPolar(x: Float, y: Float, targetFace: TargetFace): Pair<Float, Float> {
    val radius = sqrt(((targetFace.diameter / 2) - x).toDouble().pow(2.0) + ((targetFace.diameter / 2) - y).toDouble().pow(2.0)).toFloat()
    var angle = atan((((targetFace.diameter/2) - x)/((targetFace.diameter/2) - y)))
    if (((targetFace.diameter/2) - y) < 0) {
        angle += Math.PI.toFloat()
    }
    return Pair(angle, radius)
}

fun cmPolarToCoordinates(angle: Float, radius: Float, targetFace: TargetFace): Pair<Float, Float> {
    val x = ((targetFace.diameter/2)- sin(angle)*radius)
    val y = ((targetFace.diameter/2) - cos(angle)*radius)
    return Pair(x, y)
}

fun cmToPixel(x: Float, y: Float, targetView: TargetView, targetFace: TargetFace): Pair<Float, Float> {
    return Pair((((x /targetFace.diameter)*targetView.paddedWidth)+targetView.leftPadding), (((y /targetFace.diameter)*targetView.paddedHeight))+targetView.topPadding)

}