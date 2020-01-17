package org.toemmsche.alphavantagekt.entities

import org.toemmsche.alphavantagekt.entities.history.FXHistory
import org.toemmsche.alphavantagekt.entities.quote.ExchangeQuote
import org.toemmsche.alphavantagekt.enums.Scope

/**
 * The Asset subclass for a Foreign Exchange Pair (FX).
 *
 * @property fromSymbol The currency that is converted from.
 * @property toSymbol The currency that is converted to.
 * @property latestRate An ExchangeQuote object containing data about the current exchange rate between the two currencies.
 */
class FX(
    val fromSymbol : String,
    val toSymbol : String
) : Asset {

    //Current data
    var latestRate : ExchangeQuote
        get() = Currency(fromSymbol).getExchangeRateTo(toSymbol)
        private set(value) = Unit

    //Historical data
    override val intradayHistory: FXHistory =
        FXHistory(this, Scope.INTRADAY)
        get() : FXHistory {
            field.fetch()
            return field
        }
    override val dailyHistory: FXHistory =
        FXHistory(this, Scope.DAILY)
        get() : FXHistory {
            field.fetch()
            return field
        }
    override val weeklyHistory: FXHistory =
        FXHistory(this, Scope.WEEKLY)
        get() : FXHistory {
            field.fetch()
            return field
        }
    override val monthlyHistory: FXHistory =
        FXHistory(this, Scope.MONTHLY)
        get() : FXHistory {
            field.fetch()
            return field
        }

    override fun toString(): String {
        return "$fromSymbol$toSymbol"
    }
}