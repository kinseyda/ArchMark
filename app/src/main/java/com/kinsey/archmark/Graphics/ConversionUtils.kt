package com.kinsey.archmark.Graphics

import com.kinsey.archmark.Model.TargetFace
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.math.tan

fun pixelToCm(x: Int, y: Int, targetView: TargetView, targetFace: TargetFace): Pair<Float, Float> {
    return Pair(((x.toFloat()/targetView.width)*targetFace.diameter), ((y.toFloat()/targetView.height)*targetFace.diameter))
}

fun cmCoordinatesToPolar(x: Int, y: Int, targetFace: TargetFace): Pair<Float, Float> {
    val radius = sqrt(((targetFace.diameter / 2) - x).toDouble().pow(2.0) + ((targetFace.diameter / 2) - y).toDouble().pow(2.0)).toFloat()
    val angle = tan(((targetFace.diameter/2 -x)/((targetFace.diameter/2) - y)))
    return Pair(angle, radius)
}