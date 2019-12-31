package com.kinsey.archmark.io

import com.kinsey.archmark.model.Arrow
import com.kinsey.archmark.model.Card
import com.kinsey.archmark.model.End
import java.io.File
import java.io.IOException
import java.io.StringBufferInputStream
import java.util.*

data class Head(var time: Long, var total: Float, var arrows: Int)

enum class State {
    //Names are what the line should be / what the machine is looking for
    HEAD,
    HEAD_TIME,
    HEAD_TOTAL,
    HEAD_ARROWS,

    BODY
}

/**
 * Finite-State-Machine based approach to loading.
 * Uses multiple regex (one for each unique line)
 * When told to just load the head of the file, will return the above Head dataclass
 */
object CardLoader {

    //Regex patterns:
    //  Head:
    private val rHead = Regex("^Head:$")
    private val rHeadTime = Regex("^Time:[0-9]+$")
    private val rHeadTotal = Regex("^Total:[0-9]+.[0-9]+$")
    private val rHeadArrows = Regex("^Arrows:[0-9]+$")

    fun loadCard(file: File): Card {
        var lineNum = 0
        var state = State.HEAD

        var card: Card? = null

        file.forEachLine lineLoop@{
            val line = it.replace(Regex("\\s"), "")
            if (line.isEmpty()) {
                return@lineLoop
            }

            when (state) {
                State.HEAD ->
                    if (rHead.matches(line)) state = State.HEAD_TIME
                    else throw IOException(makeErrorString(state, lineNum))
            }


            println(line)
            lineNum++
        }

        return Card()
    }

    fun loadHead(file: File): Head{


        return Head(0, 0f,0)
    }

    private fun makeErrorString(state: State, lineNum: Int): String {
        return ""
    }

}