package model

import blocks.Interval
import blocks.OutputSize
import java.util.*

class Stock(val information: String,
            val lastRefreshed: String,
            val symbol: String,
            val interval: Interval,
            val outputSize: OutputSize,
            val timeZone: TimeZone, val history: List<HistoricalStock>) :
        AlphaVantageData() {

}