package com.kinsey.archmark.model

/**
 * Angle in radians from north (counter clockwise is positive, clockwise is negative), distance in cm
 */
class Arrow(val angle: Float, val distance: Float, val targetFace: TargetFace, var forScore: Boolean = true) {

    fun findRing(): Ring? {
        return this.targetFace.findRing(this)
    }

}