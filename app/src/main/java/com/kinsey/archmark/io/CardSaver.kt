package com.kinsey.archmark.io

import com.kinsey.archmark.model.Card
import java.io.File

/**
 * Format:
 * Consists of header with basic information, for identifying cards quickly
 * - Time, number of arrows, notes and such (to be added at a later date)
 * Body is nested structure
 * - Every end in order, which is every arrow in chronological order
 */
object CardSaver {

    fun saveCard(card: Card, file: File) {
        var cardString = headString(card)

        file.writeText(cardString)
    }

    private fun headString(card: Card): String {
        var s = "Card:\n"
        s += "\tTime: " + card.time.toString() + "\n"
        s += "\tTotal: " + card.cumulativeScore() + "\n"
        s += "\tArrows: " + card.allArrows().size + "\n"
        return s
    }

}