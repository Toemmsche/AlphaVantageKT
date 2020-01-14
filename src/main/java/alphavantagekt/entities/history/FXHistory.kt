package main.java.kotlin.entities.history

import main.java.kotlin.alphavantage.Requester
import main.java.kotlin.entities.FX
import main.java.kotlin.entities.quote.HistoricalQuote
import main.java.kotlin.enums.Scope


class FXHistory(
    override val underlyingAsset: FX,
    scope: Scope
) : History(scope) {

    override fun update(): Boolean {
        data = Requester.getFXData(underlyingAsset.fromSymbol, underlyingAsset.toSymbol, scope, null)
        return true
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("From_Currency: ${underlyingAsset.fromSymbol} To_Currency: ${underlyingAsset.toSymbol} Scope:${scope.FXFormatted}\n")
        for (q: HistoricalQuote in data) {
            sb.append("$q\n")
        }
        sb.dropLast(2)
        return sb.toString()
    }

}