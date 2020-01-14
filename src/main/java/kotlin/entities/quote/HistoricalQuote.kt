package main.java.kotlin.entities.quote

import java.util.*

data class HistoricalQuote(
            val timestamp : Date,
            val open: Double,
            val high : Double,
            val low : Double,
            val close : Double,
            val volume : Long?
)