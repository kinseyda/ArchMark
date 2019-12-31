package com.kinsey.archmark.model

class End {
    var arrows: MutableList<Arrow> = mutableListOf<Arrow>()

    fun addArrow(arrow: Arrow) {
        this.arrows.add(arrow)
    }

    fun removeLastArrow() {
        if (this.arrows.size > 0) {
            this.arrows.removeAt(this.arrows.size - 1)
        }
    }

    /**
     * Return the total score of every arrow shot this end
     */
    fun endTotal(): Float = arrows.map { it.findScore() }.sum()

}