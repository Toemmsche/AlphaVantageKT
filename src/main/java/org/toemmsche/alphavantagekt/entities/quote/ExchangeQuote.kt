package org.toemmsche.alphavantagekt.entities.quote

import java.util.*

/**
 * A data class to wrap information provided by the API on exchange rates between to currencies.
 *
 * @property timestamp The exact time at which the exchange rate was measured.
 * @property fromCurrency The currency that was converted from.
 * @property toCurrency The currency that was converted to.
 * @property rate The exchange rate at the timestamp.
 * @property bid The bid value.
 * @property ask The ask value.
 */
data class ExchangeQuote(val timestamp : Date,
                    val fromCurrency : String,
                    val toCurrency : String,
                    val rate : Double,
                    val bid : Double,
                    val ask : Double)