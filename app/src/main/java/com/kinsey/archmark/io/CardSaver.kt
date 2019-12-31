package com.kinsey.archmark.io

import com.kinsey.archmark.model.Arrow
import com.kinsey.archmark.model.Card
import com.kinsey.archmark.model.End
import java.io.File
import kotlin.time.milliseconds

/**
 * Format:
 * Consists of header with basic information, for identifying cards quickly
 * - Time, number of arrows, notes and such (to be added at a later date)
 * Body is nested structure
 * - Every end in order, which is every arrow in chronological order
 * Outside of body you have the TargetFace
 * - A float diameter, then key-value pairs of ring sizes and scores
 */
object CardSaver {

	fun saveCard(card: Card, file: File) =
		file.writeText(headString(card) + bodyString(card))

	private fun headString(card: Card): String =
			String.format(
				"""
				|Head:
				|	Time: %d
				|	Total: %f
				|	Arrows: %d
				|
				""".trimMargin(),
				card.time, card.cumulativeScore(), card.allArrows().size)

    private fun bodyString(card: Card): String =
            "Body:\n" +
            card.ends.filter { it.arrows.size > 0 }
            .map(::endString).joinToString("")

    private fun endString(end: End): String =
            "\tEnd:\n" +
            end.arrows.map(::arrowString).joinToString("")

    private fun arrowString(arrow: Arrow): String =
            String.format(
            """
            |		Arrow:
            |			angle: %f
            |			distance: %f
            |			forScore: %b
            |
            """.trimMargin(),
            arrow.angle, arrow.distance, arrow.forScore)

}