package entities.history

import alphavantage.Requester
import entities.quote.Quote
import enums.Scope

class ShareHistory(val symbol : String,
                   scope : Scope
) : History(scope){

    override fun update() {
        data = Requester.getShareData(symbol, scope, null)
    }

    override fun toString() : String {
        val sb = StringBuilder()
        sb.append("Stock: $symbol Scope:${scope.ShareFormatted}\n")
        for (q : Quote in data) {
            sb.append("$q\n")
        }
        sb.dropLast(2)
        return sb.toString()
    }

}