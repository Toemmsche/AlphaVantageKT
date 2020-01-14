package main.java.kotlin.entities

import main.java.kotlin.alphavantage.Requester
import main.java.kotlin.entities.quote.ExchangeQuote

open class Currency(val symbol: String) {

    fun convertTo(toCurrency: Currency): ExchangeQuote {
        return Requester.getExchangeRate(symbol, toCurrency.symbol)
    }

    fun convertFrom(toCurrency: Currency): ExchangeQuote {
        return Requester.getExchangeRate(toCurrency.symbol, symbol)
    }

    fun convertTo(toSymbol: String): ExchangeQuote {
        return Requester.getExchangeRate(symbol, toSymbol)
    }

    fun convertFrom(fromSymbol: String): ExchangeQuote {
        return Requester.getExchangeRate(fromSymbol, this.symbol)
    }
}