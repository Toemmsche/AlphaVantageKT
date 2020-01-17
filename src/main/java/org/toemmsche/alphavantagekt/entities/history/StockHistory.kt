package org.toemmsche.alphavantagekt.entities.history

import org.toemmsche.alphavantagekt.connection.Requester
import org.toemmsche.alphavantagekt.entities.Stock
import org.toemmsche.alphavantagekt.enums.Interval
import org.toemmsche.alphavantagekt.enums.Scope
import java.lang.UnsupportedOperationException

/**
 * The history subclass for a stock.
 */
class StockHistory(
    override val underlyingAsset: Stock,
    scope: Scope
) : History(scope) {

    override fun update(): StockHistory {
        data = Requester.getShareData(underlyingAsset.symbol, scope, null)
        return this
    }

    override fun update(interval: Interval):StockHistory {
        if (scope != Scope.INTRADAY) throw UnsupportedOperationException("Parameter 'interval' can only be used with intraday scope")
        data = Requester.getShareData(underlyingAsset.symbol, scope, interval)
        return this
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("stock: ${underlyingAsset.symbol}, scope:${scope.stockFormatted}\n")
        for (q in data) {
            sb.append("$q\n")
        }
        sb.dropLast(2)
        return sb.toString()
    }

}