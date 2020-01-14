package alphavantagekt

import alphavantagekt.alphavantage.Requester
import alphavantagekt.entities.Stock
import alphavantagekt.enums.IndicatorInterval
import alphavantagekt.enums.Interval


fun provideKey(key : String) {
    Requester.key = key
}

fun main() {
    provideKey("CSE3RJSJLAVEG0HL")
    //println(Cryptocurrency("BTC","USD").dailyHistory)
    println(Stock("AMD").intradayHistory.update(Interval.ONE_MINUTE))
    //println(FX("EUR", "CNY").weeklyHistory)
    //println(Stock("AMD").getIndicator("MAMA", IndicatorInterval.FIFTEEN_MINUTES, mapOf("series_type" to "high")))
    //println(Currency("JPY").getExchangeRateFrom("USD"))
}