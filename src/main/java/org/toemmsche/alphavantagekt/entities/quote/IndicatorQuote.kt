package org.toemmsche.alphavantagekt.entities.quote

import java.util.*

/**
 * A data class to wrap historical data of an indicator provided by the API.
 *
 * @property timestamp The exact time at date at which the indicator values were determined.
 * @property values A map containing all the values of the indicator at the timestamp.
 *                  Most indicators will have a single value, but some have multiple.
 */
data class IndicatorQuote(val timestamp : Date,
                          val values : Map<String, Double>
                         )