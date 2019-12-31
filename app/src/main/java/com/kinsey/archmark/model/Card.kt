package com.kinsey.archmark.model

import java.util.*

class Card(val time: Long = System.currentTimeMillis()): Observable() {
    var ends: MutableList<End> = mutableListOf<End>()
    var targetFace = TargetFace(listOf(20f, 18f, 16f, 14f, 12f, 10f, 8f, 6f, 4f, 2f, 1f), listOf(1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f, 11f), 40f)
    
    init{
        newEnd()
    }

    fun cumulativeScore(): Float = cumulativeScore(ends.size-1)

    fun cumulativeScore(upTo: Int): Float = ends.subList(0, upTo+1).map { it.endTotal() }.sum()
    
    fun allArrows(): List<Arrow> {
        val lst: MutableList<Arrow> = mutableListOf()
        for (end in ends) {
            lst += end.arrows
        }
        return lst
    }
    
    fun currentEnd(): End {
        return this.ends.last()
    }

    fun newEnd() {
        if (this.ends.size == 0 || this.currentEnd().arrows.isNotEmpty()) {
            this.ends.add(End())
            this.change()
        }
    }

    fun addArrow(arrow: Arrow) {
        currentEnd().addArrow(arrow)
        this.change()
    }

    fun removeLastArrow() {
        currentEnd().removeLastArrow()
        this.change()
    }

    fun hasEnds() = ends.size > 0

    fun getMostArrows(): Int {
        return Collections.max(ends.map { it.arrows.size })
    }

    private fun change() {
        this.setChanged()
        this.notifyObservers()
    }

    fun clear() {
        this.ends = mutableListOf()
        this.newEnd()
    }
}