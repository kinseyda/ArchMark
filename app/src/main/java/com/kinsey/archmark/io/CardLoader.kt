package com.kinsey.archmark.io

import com.kinsey.archmark.model.Arrow
import com.kinsey.archmark.model.Card
import java.io.File
import java.io.IOException

data class Head(var time: Long, var total: Float, var arrows: Int)

enum class State {
    //Names are what the line should be / what the machine is looking for
    HEAD,
    HEAD_TIME,
    HEAD_TOTAL,
    HEAD_ARROWS,

    BODY,
    BODY_END_ARROW,
    BODY_ARROW_ANGLE,
    BODY_ARROW_DISTANCE,
    BODY_ARROW_FORSCORE
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
    private val rHeadTime = Regex("^Time:([0-9]+)$")
    private val rHeadTotal = Regex("^Total:([0-9]+.[0-9]+)$")
    private val rHeadArrows = Regex("^Arrows:([0-9]+)$")

    private val rBody = Regex("^Body:$")
    private val rBodyEnd = Regex("^End:$")
    private val rBodyArrow = Regex("^Arrow:$")
    private val rBodyArrowAngle = Regex("^angle:(-?[0-9]+.[0-9]+)$")
    private val rBodyDistance = Regex("^distance:([0-9]+.[0-9]+)$")
    private val rBodyForScore = Regex("^forScore:(true|false)$")

    fun loadCard(file: File): Card {
        var lineNum = 0
        var state = State.HEAD

        var card: Card? = null

        var arrowParams = mutableListOf<String>()

        file.forEachLine lineLoop@{
            val line = it.replace(Regex("\\s"), "")

//            println(String.format("Line %d, State %s: %s", lineNum, state.toString(), line))

            if (line.isEmpty()) {
                return@lineLoop
            }

            when (state) {
                State.HEAD ->
                    if (rHead.matches(line)) state = State.HEAD_TIME
                    else throw IOException(makeErrorString(state, lineNum))

                State.HEAD_TIME ->
                    if (rHeadTime.matches(line)) {
                        card =
                            Card((rHeadTime.find(line)?.destructured?.component1()?.toLong()) ?: 0)
                        state = State.HEAD_TOTAL
                    } else throw IOException(makeErrorString(state, lineNum))

                State.HEAD_TOTAL ->
                    if (rHeadTotal.matches(line)) state = State.HEAD_ARROWS
                    else throw IOException(makeErrorString(state, lineNum))

                State.HEAD_ARROWS ->
                    if (rHeadArrows.matches(line)) state = State.BODY
                    else throw IOException(makeErrorString(state, lineNum))

                State.BODY ->
                    if (rBody.matches(line)) state = State.BODY_END_ARROW
                    else throw IOException(makeErrorString(state, lineNum))

                State.BODY_END_ARROW ->
                    state = when {
                        rBodyEnd.matches(line) -> {
                            card!!.newEnd()
                            State.BODY_END_ARROW
                        }
                        rBodyArrow.matches(line) -> State.BODY_ARROW_ANGLE
                        else -> throw IOException(makeErrorString(state, lineNum))
                    }

                State.BODY_ARROW_ANGLE ->
                    if (rBodyArrowAngle.matches(line)) {
                        arrowParams.add(
                            rBodyArrowAngle.find(line)?.destructured?.component1() ?: "0.0"
                        )
                        state = State.BODY_ARROW_DISTANCE
                    } else throw IOException(makeErrorString(state, lineNum))

                State.BODY_ARROW_DISTANCE ->
                    if (rBodyDistance.matches(line)) {
                        arrowParams.add(
                            rBodyDistance.find(line)?.destructured?.component1() ?: "0.0"
                        )
                        state = State.BODY_ARROW_FORSCORE
                    } else throw IOException(makeErrorString(state, lineNum))

                State.BODY_ARROW_FORSCORE ->
                    if (rBodyForScore.matches(line)) {
                        arrowParams.add(
                            rBodyForScore.find(line)?.destructured?.component1() ?: "true"
                        )
                        card!!.addArrow(
                            Arrow(
                                arrowParams[0].toFloat(),
                                arrowParams[1].toFloat(),
                                card!!,
                                arrowParams[2].toBoolean()
                            )
                        )
                        card!!.deselect()
                        arrowParams.clear()
                        state = State.BODY_END_ARROW
                    } else throw IOException(makeErrorString(state, lineNum))
            }


            lineNum++
        }

        return card!!
    }

    fun loadHead(file: File): Head {

        var lineNum = 0
        var state = State.HEAD

        var head = Head(0, 0f, 0)


        file.forEachLine lineLoop@{
            val line = it.replace(Regex("\\s"), "")

            if (line.isEmpty()) {
                return@lineLoop
            }

            when (state) {
                State.HEAD ->
                    if (rHead.matches(line)) state = State.HEAD_TIME
                    else throw IOException(makeErrorString(state, lineNum))

                State.HEAD_TIME ->
                    if (rHeadTime.matches(line)) {
                        head.time =
                            (rHeadTime.find(line)?.destructured?.component1()?.toLong()) ?: 0
                        state = State.HEAD_TOTAL
                    } else throw IOException(makeErrorString(state, lineNum))

                State.HEAD_TOTAL ->
                    if (rHeadTotal.matches(line)) {
                        head.total =
                            (rHeadTotal.find(line)?.destructured?.component1()?.toFloat()) ?: 0f
                        state = State.HEAD_ARROWS
                    } else throw IOException(makeErrorString(state, lineNum))

                State.HEAD_ARROWS ->
                    if (rHeadArrows.matches(line)) {
                        head.arrows =
                            (rHeadTotal.find(line)?.destructured?.component1()?.toInt()) ?: 0
                        state = State.BODY
                    } else throw IOException(makeErrorString(state, lineNum))
                State.BODY -> return@lineLoop
                else -> throw IOException(makeErrorString(state, lineNum))
            }
            lineNum++
        }
        return head
    }

    private fun makeErrorString(state: State, lineNum: Int): String {
        val s = when (state) {
            State.HEAD -> "start of file head"
            State.HEAD_TIME -> "card time"
            State.HEAD_TOTAL -> "cumulative total"
            State.HEAD_ARROWS -> "quantity of arrows"
            State.BODY -> "start of file body"
            State.BODY_END_ARROW -> "start of arrow or new end"
            State.BODY_ARROW_ANGLE -> "arrow angle"
            State.BODY_ARROW_DISTANCE -> "arrow distance"
            State.BODY_ARROW_FORSCORE -> "arrow forScore value"
        }

        return String.format("Error loading card: Line %d, Expected %s", lineNum, s)
    }

}