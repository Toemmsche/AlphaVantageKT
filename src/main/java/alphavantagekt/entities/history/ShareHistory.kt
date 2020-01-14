package alphavantagekt.entities.history

import alphavantagekt.alphavantage.Requester
import alphavantagekt.entities.Stock
import alphavantagekt.enums.Interval
import alphavantagekt.enums.Scope
import java.lang.UnsupportedOperationException


class ShareHistory(
    override val underlyingAsset: Stock,
    scope: Scope
) : History(scope) {

    override fun update(): ShareHistory {
        data = Requester.getShareData(underlyingAsset.symbol, scope, null)
        return this
    }

    override fun update(interval: Interval):ShareHistory {
        if (scope != Scope.INTRADAY) throw UnsupportedOperationException("Parameter 'interval' can only be used with intraday scope")
        data = Requester.getShareData(underlyingAsset.symbol, scope, interval)
        return this
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("stock: ${underlyingAsset.symbol}, scope:${scope.shareFormatted}\n")
        for (q in data) {
            sb.append("$q\n")
        }
        sb.dropLast(2)
        return sb.toString()
    }

}