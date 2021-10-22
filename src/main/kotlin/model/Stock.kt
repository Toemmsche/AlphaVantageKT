package model

import blocks.Function
import blocks.Interval
import blocks.OutputSize
import java.time.ZonedDateTime
import java.util.*

class Stock(function: Function,
            val information: String?,
            val symbol: String,
            val lastRefreshed: ZonedDateTime,
            val interval: Interval?, // not present in every response
            val outputSize: OutputSize?, // not present in every response
            val timeZone: TimeZone,
            val history: List<HistoricalStock>
) : AlphaVantageData() {


}