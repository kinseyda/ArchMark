package com.kinsey.archmark.model

class End {
    var arrows: MutableList<Arrow> = mutableListOf<Arrow>()

    fun addArrow(arrow: Arrow) {
        this.arrows.add(arrow)
        this.arrows.sortBy { it.distance }
    }

    /**
     * Return the total score of every arrow shot this end
     */
    fun endTotal(): Float = arrows.map { it.findScore() }.sum()

}