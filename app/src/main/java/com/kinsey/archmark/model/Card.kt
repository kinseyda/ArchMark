package com.kinsey.archmark.model

import java.util.*

class Card {
    var ends: MutableList<End> = mutableListOf<End>()
    var targetFace = TargetFace(listOf(20f, 18f, 16f, 14f, 12f, 10f, 8f, 6f, 4f, 2f, 1f), listOf(1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f, 11f), 40f)
    init{
        newEnd()
    }

    fun cumulativeScore(): Float = cumulativeScore(ends.size-1)

    fun cumulativeScore(upTo: Int): Float = ends.subList(0, upTo+1).map { it.endTotal() }.sum()
    
    fun currentEnd(): End {
        return this.ends.last()
    }

    fun newEnd() {
        this.ends.add(End())
    }

    fun addArrow(arrow: Arrow) {
        currentEnd().addArrow(arrow)
    }

    fun hasArrows() = ends.size > 0

    fun getMostArrows(): Int {
        return Collections.max(ends.map { it.arrows.size })
    }

    fun clear() {
        ends.clear()
    }

}