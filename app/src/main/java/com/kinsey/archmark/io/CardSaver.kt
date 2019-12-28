package com.kinsey.archmark.io

import com.kinsey.archmark.model.Arrow
import com.kinsey.archmark.model.Card
import com.kinsey.archmark.model.End
import java.io.File

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

    fun saveCard(card: Card, file: File) {
        var cardString = "Card:\n"

        cardString += headString(card)
        cardString += bodyString(card)

        file.writeText(cardString)
    }

    private fun headString(card: Card): String {
        var s = "\tHead:\n"
        s += "\t\tTime: " + card.time.toString() + "\n"
        s += "\t\tTotal: " + card.cumulativeScore() + "\n"
        s += "\t\tArrows: " + card.allArrows().size + "\n"
        return s
    }

    private fun bodyString(card: Card): String {
        var s = "\tBody:\n"
        for (end in card.ends) {
            if (end.arrows.size == 0) {
                continue
            }
            s += endString(end)
        }
        return s
    }

    private fun endString(end: End): String {
        var s = "\t\tEnd:\n"
        for (arrow in end.arrows) {
            s += arrowString(arrow)
        }
        return s
    }

    private fun arrowString(arrow: Arrow): String {
        var s = "\t\t\tArrow:\n"
        s += "\t\t\t\tangle: " + arrow.angle + "\n"
        s += "\t\t\t\tdistance: " + arrow.distance + "\n"
        s += "\t\t\t\tforScore: " + arrow.forScore + "\n"
        return s
    }

}