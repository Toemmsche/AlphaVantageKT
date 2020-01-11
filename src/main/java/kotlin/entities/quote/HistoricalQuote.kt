package main.java.kotlin.entities.quote

import java.util.*

class HistoricalQuote(
            timestamp : Date,
            val open: Double,
            val high : Double,
            val low : Double,
            val close : Double,
            val volume : Long?
) : Quote(timestamp) {

    override fun toString() : String {
        return "[$timestamp, $open, $high, $low, $close, ${volume?: "N/A"}]"
    }

}