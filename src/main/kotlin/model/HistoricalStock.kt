package model

import java.time.ZonedDateTime

class HistoricalStock(
        date: ZonedDateTime,
        val open: Double,
        val high: Double,
        val low: Double,
        val close: Double,
        val volume: Int,
        //TODO add attributes
) : Historical(date) {

}