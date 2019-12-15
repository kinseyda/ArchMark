package com.kinsey.archmark.Model

class End {
    var arrows: MutableList<Arrow> = mutableListOf<Arrow>()

    fun addArrow(arrow: Arrow) {
        this.arrows.add(arrow)
    }

    /**
     * Return the total score of every arrow shot this end
     */
    fun endTotal(): Float {
        var c = 0f
        for (arrow in arrows) {
            c += arrow.findRing()?.score ?: 0f
        }
        return c
    }

}