package alphavantagekt.entities.history

import alphavantagekt.alphavantage.Requester
import alphavantagekt.entities.Stock
import alphavantagekt.entities.quote.HistoricalQuote

import alphavantagekt.entities.quote.IndicatorQuote
import alphavantagekt.enums.IndicatorInterval

class IndicatorHistory(
    val symbol: String,
    val underlyingStock: Stock,
    val interval: IndicatorInterval,
    var data: MutableList<IndicatorQuote> = ArrayList<IndicatorQuote>()
) : MutableList<IndicatorQuote> by data {

    constructor(symbol: String, underlyingStock: Stock, interval: IndicatorInterval, params: Map<String, String>) : this(
        symbol,
        underlyingStock,
        interval
    ) {
        update(params)

    }

    override fun toString() : String {
        val sb = StringBuilder()
        sb.append("indicator: $symbol, stock=${underlyingStock.symbol}, interval=${interval.formatted}\n")
        for (q in data) {
            sb.append("$q\n")
        }
        sb.dropLast(2)
        return sb.toString()
    }

    fun update(params: Map<String, String>): IndicatorHistory{
        data = Requester.getIndicatorData(symbol, underlyingStock.symbol, interval, params)
        return this
    }

}