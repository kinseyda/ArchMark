package com.kinsey.archmark.io

import com.kinsey.archmark.model.Card
import java.io.File
import java.io.IOException
import java.util.*

data class Head(val time: Long, val total: Float, val arrows: Int)

/**
 * Finite-State-Machine based approach to loading.
 * Uses multiple regex (one for each unique line)
 * When told to just load the head of the file, will return the above Head dataclass
 */
object CardLoader {

    //Regex patterns:
    //  Head:
//    private val

    fun loadCard(file: File): Card {


        return Card()
    }

    fun loadHead(file: File): Head{


        return Head(0, 0f,0)
    }

}