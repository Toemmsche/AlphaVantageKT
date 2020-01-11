package main.java.kotlin.entities

import main.java.kotlin.alphavantage.Requester
import main.java.kotlin.entities.quote.ExchangeQuote

open class Currency(val fromSymbol : String) {

    fun convertTo(currency : Currency) : ExchangeQuote {
        return Requester.getExchangeRate(fromSymbol, currency.fromSymbol)
    }
    fun convertFrom(currency : Currency) : ExchangeQuote {
        return Requester.getExchangeRate(currency.fromSymbol, fromSymbol)
    }
}