package model.stock

import model.AlphaVantageData
import query.Function
import query.Interval
import query.OutputSize
import java.time.ZonedDateTime
import java.util.*

class Stock(
        val function: Function,
        val information: String?,
        val symbol: String,
        val lastRefreshed: ZonedDateTime,
        val interval: Interval?, // not present in every response
        val outputSize: OutputSize?, // not present in every response
        val timeZone: TimeZone,
        val history: List<HistoricalStock>
) : AlphaVantageData() {


}