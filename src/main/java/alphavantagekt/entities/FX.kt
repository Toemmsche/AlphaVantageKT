package alphavantagekt.entities

import alphavantagekt.entities.history.FXHistory
import alphavantagekt.entities.quote.ExchangeQuote
import alphavantagekt.enums.Scope

/**
 * The Asset subclass for a Foreign Exchange Pair (FX).
 *
 * @property fromSymbol The currency that is converted from.
 * @property toSymbol The currency that is converted to.
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