package model.stock

import java.time.ZonedDateTime

data class HistoricalStock(
        val date: ZonedDateTime,
        val open: Double,
        val high: Double,
        val low: Double,
        val close: Double,
        val adjustedClose: Double?,
        val volume: Int,
        val dividendAmount: Double?,
        val splitCoefficient: Double?
){

}