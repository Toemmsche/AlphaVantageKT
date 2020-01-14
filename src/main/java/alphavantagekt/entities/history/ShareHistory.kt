package main.java.kotlin.entities.history

import main.java.kotlin.alphavantage.Requester
import main.java.kotlin.entities.Stock
import main.java.kotlin.entities.quote.HistoricalQuote
import main.java.kotlin.enums.Scope

class ShareHistory(
    override val underlyingAsset: Stock,
    scope: Scope
) : History(scope) {

    override fun update(): Boolean {
        data = Requester.getShareData(underlyingAsset.symbol, scope, null)
        return true
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("Stock: ${underlyingAsset.symbol} Scope:${scope.shareFormatted}\n")
        for (q: HistoricalQuote in data) {
            sb.append("$q\n")
        }
        sb.dropLast(2)
        return sb.toString()
    }

}