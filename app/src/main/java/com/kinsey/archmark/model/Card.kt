package com.kinsey.archmark.model

import java.util.*

class Card {
    var ends: MutableList<End> = mutableListOf<End>()
    init{
        newEnd()
    }

    fun cumulativeScore(): Float = cumulativeScore(ends.size-1)

    fun cumulativeScore(upTo: Int): Float = ends.subList(0, upTo).map { it.endTotal() }.sum()
    
    fun currentEnd(): End {
        return this.ends.last()
    }

    fun newEnd() {
        this.ends.add(End())
    }

    fun addArrow(arrow: Arrow) {
        currentEnd().addArrow(arrow)
    }

    fun getMostArrows(): Int {
        return Collections.max(ends.map { it.arrows.size })
    }
}