package alphavantagekt.entities

import alphavantagekt.connection.Requester
import alphavantagekt.entities.quote.ExchangeQuote


/**
 * An extendable class for a real-word currency. This class doesn't store the currency amount is only used for API calls.
 *
 * @property symbol The currency symbol, e.g. EUR
 */
open class Currency(val symbol: String) {

    /**
     * @param toCurrency The currency this currency will be converted to.
     * @return All the data surrounding the exchange rate of the two currencies.
     */
    fun getExchangeRateTo(toCurrency: Currency): ExchangeQuote {
        return Requester.getExchangeRate(symbol, toCurrency.symbol)
    }

    /**
     * @param toCurrency The currency this currency will be converted from
     * @return All the data surrounding the exchange rate of the two currencies.
     */
    fun getExchangeRateFrom(toCurrency: Currency): ExchangeQuote {
        return Requester.getExchangeRate(toCurrency.symbol, symbol)
    }

    /**
     * @param toSymbol The currency this currency will be converted from
     * @return All the data surrounding the exchange rate of the two currencies.
     */
    fun getExchangeRateTo(toSymbol: String): ExchangeQuote {
        return Requester.getExchangeRate(symbol, toSymbol)
    }

    /**
     * @param toSymbol The currency this currency will be converted from
     * @return All the data surrounding the exchange rate of the two currencies.
     */
    fun getExchangeRateFrom(fromSymbol: String): ExchangeQuote {
        return Requester.getExchangeRate(fromSymbol, this.symbol)
    }
}