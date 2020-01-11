package main.java.kotlin.entities.quote

import java.util.*

class ExchangeQuote(timestamp : Date,
                    val fromCurrency : String,
                    val toCurrency : String,
                    val rate : Double,
                    val bid : Double,
                    val ask : Double) : Quote(timestamp)  {

    override fun toString(): String {
        return "[$timestamp, from_Currrency: $fromCurrency, to_Currency: $toCurrency, rate: $rate]"
    }
}