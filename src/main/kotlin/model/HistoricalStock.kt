package model

import java.time.ZonedDateTime
import java.util.*

class HistoricalStock(date: ZonedDateTime, val open: Double, val high: Double,
                      val low: Double, val close: Double, val volume: Int) : Historical(date) {

}