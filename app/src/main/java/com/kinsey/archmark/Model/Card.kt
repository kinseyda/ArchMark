package com.kinsey.archmark.Model

class Card {
    var ends: MutableList<End> = mutableListOf<End>()
    init{
        newEnd()
    }

    fun cumulativeScore(): Float {
        var c = 0f
        for (end in this.ends) {
            c += end.endTotal()
        }
        return c
    }

    fun currentEnd(): End {
        return ends.last()
    }

    fun newEnd() {
        ends.add(End())
    }

    fun addArrow(arrow: Arrow) {
        currentEnd().addArrow(arrow)
    }
}