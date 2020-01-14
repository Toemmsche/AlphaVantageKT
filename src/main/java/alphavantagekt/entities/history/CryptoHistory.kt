package alphavantagekt.entities.history

import alphavantagekt.alphavantage.Requester
import alphavantagekt.entities.Cryptocurrency
import alphavantagekt.enums.Interval
import alphavantagekt.enums.Scope
import java.lang.UnsupportedOperationException


class CryptoHistory(
    override val underlyingAsset: Cryptocurrency,
    scope: Scope
) : History(scope) {

    override fun update(): CryptoHistory {
        data = Requester.getCryptoData(underlyingAsset.symbol, underlyingAsset.market, scope)
        return this
    }

    override fun update(interval: Interval) : CryptoHistory {
        throw UnsupportedOperationException("Parameter 'interval' cannot be used for cryptocurrencies")
        return this
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("cryptocurrency: ${underlyingAsset.symbol}, market: ${underlyingAsset.market}, scope:${scope.cryptoFormatted}\n")
        for (q in data) {
            sb.append("$q\n")
        }
        sb.dropLast(2)
        return sb.toString()
    }
}