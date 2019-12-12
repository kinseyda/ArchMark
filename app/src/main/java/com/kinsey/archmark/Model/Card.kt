package com.kinsey.archmark.Model

class Card {
    var ends: MutableList<End> = mutableListOf<End>()


    fun addArrow(arrow: Arrow) {
        this.ends.last().addArrow(arrow)
    }
}