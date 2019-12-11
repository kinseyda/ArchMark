package com.kinsey.archmark.Model

class End {
    var arrows: MutableList<Arrow> = mutableListOf<Arrow>()

    fun addArrow(arrow: Arrow) {
        this.arrows.add(arrow)
    }
}