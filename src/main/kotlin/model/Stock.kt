package model

import blocks.Interval
import blocks.OutputSize
import java.time.ZonedDateTime
import java.util.*

class Stock(val information: String,
            val symbol: String,
            val lastRefreshed: ZonedDateTime,
            val interval: Interval,
            val outputSize: OutputSize,
            val timeZone: TimeZone, val history: List<HistoricalStock>) :
        AlphaVantageData() {


}