package com.kinsey.archmark.model

object StandardFace{
    val fortyEleven = TargetFace(listOf(20f, 18f, 16f, 14f, 12f, 10f, 8f, 6f, 4f, 2f, 1f), listOf(1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f, 11f), 40f)
}


class TargetFace(sizes: List<Float>, scores: List<Float>, val diameter: Float) {
    //TODO change this to take in a dictionary or something, so that sizes and scores are always the same length
    var rings: MutableList<Ring> = mutableListOf()

    init {
        for (i in sizes.indices) {
            rings.add(Ring(sizes[i], scores[i]))
        }
    }

    /**
     * Finds the ring that an arrow landed on
     */
    fun findRing(arrow:Arrow): Ring? {

        val distance = arrow.distance

        //First check if arrow is on target
        if (distance > (this.diameter/2)) {
            return null
        }
        
        //Find the smallest ring that has a radius greater than distance
        var smallest = rings.first()
        for (ring in this.rings) {
            if ((ring.radius > distance)&&(ring.radius < smallest.radius)) {
                smallest = ring
            }
        }
        return smallest

    }

}