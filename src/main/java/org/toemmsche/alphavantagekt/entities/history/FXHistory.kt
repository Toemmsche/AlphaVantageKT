package org.toemmsche.alphavantagekt.entities.history

import org.toemmsche.alphavantagekt.connection.Requester
import org.toemmsche.alphavantagekt.entities.FX
import org.toemmsche.alphavantagekt.enums.Interval
import org.toemmsche.alphavantagekt.enums.Scope
import java.lang.UnsupportedOperationException


/**
 * The history subclass for a Foreign Exchange Pair (FX).
 */
class FXHistory(
    override val underlyingAsset: FX,
    scope: Scope
) : History(scope) {

    override fun update(): FXHistory {
        data = Requester.getFXData(underlyingAsset.fromSymbol, underlyingAsset.toSymbol, scope, null)
        return this
    }

    override fun update(interval: Interval): FXHistory {
        if (scope != Scope.INTRADAY) throw UnsupportedOperationException("Parameter 'interval' can only be used with intraday scope")
        data = Requester.getFXData(underlyingAsset.fromSymbol, underlyingAsset.toSymbol, scope, interval)
        return this
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("from_Currency: ${underlyingAsset.fromSymbol}, to_Currency: ${underlyingAsset.toSymbol}, scope:${scope.FXFormatted}\n")
        for (q in data) {
            sb.append("$q\n")
        }
        sb.dropLast(2)
        return sb.toString()
    }

}