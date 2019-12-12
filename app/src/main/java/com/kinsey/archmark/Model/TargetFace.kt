package com.kinsey.archmark.Model

class TargetFace(sizes: List<Float>, scores: List<Float>, val diameter: Float) {
    //TODO change this to take in a dictionary or something, so that sizes and scores are always the same length
    var rings: MutableList<Ring> = mutableListOf()

    init {
        for (i in sizes.indices) {
            rings.add(Ring(sizes[i], scores[i]))
        }
    }



}