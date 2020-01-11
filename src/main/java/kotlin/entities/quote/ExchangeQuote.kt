package main.java.kotlin.entities.quote

import java.util.*

class ExchangeQuote(timestamp : Date,
                    val fromSymbol : String,
                    val toSymbol : String,
                    val rate : Double,
                    val bid : Double,
                    val ask : Double) : Quote(timestamp)  {
    override fun toString(): String {
        return "[$timestamp, from_Currrency: $fromSymbol, to_Currency: $toSymbol, rate: $rate]"
    }
}