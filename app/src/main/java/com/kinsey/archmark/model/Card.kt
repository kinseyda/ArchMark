package com.kinsey.archmark.model

class Card {
    var ends: MutableList<End> = mutableListOf<End>()
    init{
        newEnd()
    }

    fun cumulativeScore(): Float = ends.map { it.endTotal() }.sum()

    fun currentEnd(): End {
        return this.ends.last()
    }

    fun newEnd() {
        this.ends.add(End())
    }

    fun addArrow(arrow: Arrow) {
        currentEnd().addArrow(arrow)
    }
}