package alphavantagekt.entities.quote

import java.util.*

data class ExchangeQuote(val timestamp : Date,
                    val fromCurrency : String,
                    val toCurrency : String,
                    val rate : Double,
                    val bid : Double,
                    val ask : Double)