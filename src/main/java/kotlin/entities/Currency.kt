package main.java.kotlin.entities

import main.java.kotlin.alphavantage.Requester

open class Currency(val fromSymbol : String) {

    fun convertTo(currency : Currency) : Double {
        return Requester.getExchangeRate(fromSymbol, currency.fromSymbol).close
    }
    fun convertFrom(currency : Currency) : Double {
        return Requester.getExchangeRate(currency.fromSymbol, fromSymbol).close
    }
}