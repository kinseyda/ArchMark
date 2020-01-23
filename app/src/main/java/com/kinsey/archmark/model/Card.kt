package com.kinsey.archmark.model

import java.util.*

class Card(val time: Long = System.currentTimeMillis()): Observable() {
    var ends: MutableList<End> = mutableListOf<End>()
    lateinit var currentEnd: End
    init{
        newEnd()
    }

    var targetFace = StandardFace.fortyEleven

    val defaultEndSize = 3

    fun cumulativeScore(): Float = cumulativeScore(ends.size-1)

    fun cumulativeScore(upTo: Int): Float = ends.subList(0, upTo+1).map { it.endTotal() }.sum()
    
    fun allArrows(): List<Arrow> {
        val lst: MutableList<Arrow> = mutableListOf()
        for (end in ends) {
            lst += end.arrows
        }
        return lst
    }

    fun scoreMatrix(): List<List<Float>> = this.ends.map { it.scoreList() }

    fun newEnd() {
        //Switch to last end if empty
        if (this.ends.size > 0 && this.ends.last().arrows.isEmpty()) {
            this.currentEnd = this.ends.last()
            this.change()
        }
        else if (this.ends.size == 0 || this.currentEnd.arrows.isNotEmpty()) {
            this.ends.add(End())
            this.currentEnd = this.ends.last()
            this.change()
        }
    }

    fun addArrow(arrow: Arrow) {
        currentEnd.addArrow(arrow)
        this.change()
    }

    fun removeLastArrow() {
        currentEnd.removeLastArrow()
        this.change()
    }

    fun hasEnds() = ends.size > 0

    fun getMostArrows(): Int {
        return Collections.max(ends.map { it.arrows.size })
    }

    fun setCurrentEndTo(i: Int) {
        this.currentEnd = this.ends[i]
        this.change()
    }

    fun setCurrentArrowTo(i: Int) {
        var indexOrNull: Int? = i
        if (i == this.currentEnd.selected) {
            indexOrNull = null
        }
        this.currentEnd.selected = indexOrNull
        this.change()
    }

    fun change() {
        this.setChanged()
        this.notifyObservers()
    }

    fun clear() {
        this.ends = mutableListOf()
        this.newEnd()
    }

    fun copy(): Card {
        val copy = Card(this.time)
        copy.ends = this.ends.map {it.copy(copy)}.toMutableList()
        copy.setCurrentEndTo(this.ends.indexOf(this.currentEnd))
        return copy
    }
}