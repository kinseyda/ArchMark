package com.kinsey.archmark.Model

class Target {

    var rings: MutableList<Ring> = mutableListOf()

    constructor(sizes: List<Float>, scores: List<Float>) {
        //TODO change this to take in a dictionary or something, so that sizes and scores are always the same length

        for (i in sizes.indices) {
            rings.add(Ring(sizes[i], scores[i]))
        }

    }

}