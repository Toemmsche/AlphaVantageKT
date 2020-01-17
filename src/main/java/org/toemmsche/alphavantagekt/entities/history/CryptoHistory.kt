package org.toemmsche.alphavantagekt.entities.history

import org.toemmsche.alphavantagekt.connection.Requester
import org.toemmsche.alphavantagekt.entities.Cryptocurrency
import org.toemmsche.alphavantagekt.enums.Interval
import org.toemmsche.alphavantagekt.enums.Scope
import java.lang.UnsupportedOperationException

/**
 * The history subclass for a cryptocurrency.
 */
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