package alphavantagekt.entities

import alphavantagekt.entities.history.CryptoHistory
import alphavantagekt.entities.quote.ExchangeQuote
import alphavantagekt.enums.Scope
import java.lang.UnsupportedOperationException

/**
 * The Asset sublcass for a cryptocurrency.
 *
 * @property market The market (currency) in which the cryptocurrency is evaluated.
 * @property latestRate An ExchangeQuote object containing data about the current exchange rate between this cryptoccureny
 *                      and the underlying market currency.
 */
class Cryptocurrency(symbol : String,
                     val market : String) : Asset, Currency(symbol) {

    constructor(symbol : String, currency : Currency) : this(symbol, currency.symbol){}

    //Current data
    var latestRate : ExchangeQuote
        get() = Currency(market).getExchangeRateTo(market)
        private set(value) = Unit

    //Historical data
    override val intradayHistory: CryptoHistory
        get() : CryptoHistory {
            throw UnsupportedOperationException()
        }
    override val dailyHistory:CryptoHistory =
        CryptoHistory(this, Scope.DAILY)
        get() : CryptoHistory {
            field.fetch()
            return field
        }
    override val weeklyHistory: CryptoHistory =
        CryptoHistory(this, Scope.WEEKLY )
        get() : CryptoHistory {
            field.fetch()
            return field
        }
    override val monthlyHistory: CryptoHistory =
        CryptoHistory(this, Scope.MONTHLY)
        get() : CryptoHistory {
            field.fetch()
            return field
        }

    override fun toString(): String {
        return symbol
    }
}