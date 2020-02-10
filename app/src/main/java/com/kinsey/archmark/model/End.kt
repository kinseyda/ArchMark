package com.kinsey.archmark.model

class End {
    var arrows: MutableList<Arrow> = mutableListOf<Arrow>()
    var selected: Int? = null

    fun addArrow(arrow: Arrow) {
        selected?.let { arrows[selected!!] = arrow} ?: run { this.arrows.add(arrow) ; selected = this.arrows.size-1}
    }

    fun removeLastArrow() {
        if (this.arrows.size > 0) {
            this.arrows.removeAt(this.arrows.size - 1)
        }
    }

    /**
     * Return the total score of every arrow shot this end
     */
    fun endTotal(): Float = scoreList().sum()

    fun scoreList(): List<Float> = arrows.map{it.findScore()}

    fun copy(newCard: Card): End {
        val copy = End()
        copy.arrows = this.arrows.map {it.copy(newCard)}.toMutableList()
        return copy
    }

}