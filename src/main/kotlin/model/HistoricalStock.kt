package model

import java.util.*

data class HistoricalStock(val date: Date, val open: Double, val high: Double,
                      val low: Double, val volume: Int) : Historical(date) {

}