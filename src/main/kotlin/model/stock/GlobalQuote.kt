package model.stock

import java.time.LocalDate
import java.time.ZonedDateTime

data class GlobalQuote(
        val symbol : String,
        val open: Double,
        val high: Double,
        val low: Double,
        val price: Double,
        val volume : Int,
        val latestTradingDay: LocalDate,
        val previousClose: Double,
        val change: Double,
        val changePercent: Double
) {
}