package main.java.kotlin.entities.history

import main.java.kotlin.alphavantage.Requester
import main.java.kotlin.entities.Stock
import kotlin.entities.quote.IndicatorQuote
import kotlin.enums.IndicatorInterval

class IndicatorHistory(
    val symbol: String,
    val underlyingStock: Stock,
    val interval: IndicatorInterval,
    var data: MutableList<IndicatorQuote> = ArrayList<IndicatorQuote>()
) : MutableList<IndicatorQuote> by data {

    constructor(symbol: String,underlyingStock: Stock, interval: IndicatorInterval, params: Map<String, String>) : this(
        symbol,
        underlyingStock,
        interval
    ) {
        update(params)

    }

    fun update(params: Map<String, String>): Boolean {
        data = Requester.getIndicatorData(symbol, underlyingStock.symbol, interval, params)
        return true
    }

}