package alphavantagekt.entities

import alphavantagekt.alphavantage.Requester
import alphavantagekt.entities.quote.ExchangeQuote


open class Currency(val symbol: String) {

    fun getExchangeRateTo(toCurrency: Currency): ExchangeQuote {
        return Requester.getExchangeRate(symbol, toCurrency.symbol)
    }

    fun getExchangeRateFrom(toCurrency: Currency): ExchangeQuote {
        return Requester.getExchangeRate(toCurrency.symbol, symbol)
    }

    fun getExchangeRateTo(toSymbol: String): ExchangeQuote {
        return Requester.getExchangeRate(symbol, toSymbol)
    }

    fun getExchangeRateFrom(fromSymbol: String): ExchangeQuote {
        return Requester.getExchangeRate(fromSymbol, this.symbol)
    }
}