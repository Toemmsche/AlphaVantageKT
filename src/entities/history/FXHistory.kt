package entities.history

import alphavantage.Requester
import entities.quote.Quote
import enums.Scope


class FXHistory(val fromSymbol : String,
                val toSymbol : String,
                scope : Scope
) : History(scope){

    override fun update() {
        data = Requester.getFXData(fromSymbol, toSymbol, scope, null)
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("From_Currency: $fromSymbol To_Currency: $toSymbol Scope:${scope.ShareFormatted}\n")
        for (q : Quote in data) {
            sb.append("$q\n")
        }
        sb.dropLast(2)
        return sb.toString()
    }

}