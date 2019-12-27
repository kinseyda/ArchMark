package com.kinsey.archmark.model

/**
 * Angle in radians from north (counter clockwise is positive, clockwise is negative), distance in cm
 */
class Arrow(val angle: Float, val distance: Float, val card: Card, var forScore: Boolean = true) {

    fun findRing(): Ring? {
        return this.card.targetFace.findRing(this)
    }

    fun findScore(): Float {
        return this.findRing()?.score ?: 0f
    }

}