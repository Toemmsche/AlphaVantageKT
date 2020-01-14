package main.java.kotlin.entities.history

import main.java.kotlin.alphavantage.Requester
import main.java.kotlin.entities.quote.HistoricalQuote
import main.java.kotlin.enums.Scope

class ShareHistory(val symbol : String,
                   scope : Scope
) : History(scope){

    override fun update() {
        data = Requester.getShareData(symbol, scope, null)
    }

    override fun toString() : String {
        val sb = StringBuilder()
        sb.append("Stock: $symbol Scope:${scope.ShareFormatted}\n")
        for (q : HistoricalQuote in data) {
            sb.append("$q\n")
        }
        sb.dropLast(2)
        return sb.toString()
    }

}