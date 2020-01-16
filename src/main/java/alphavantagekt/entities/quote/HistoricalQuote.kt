package alphavantagekt.entities.quote

import java.util.*

/**
 * A data class to wrap historical data of an asset provided by the API
 *
 * @property timestamp The beginning of the interval at which the values were observed. The lenght of the interval depends
 *                      on the scope specified during the API call.
 * @property open The price of the asset at the timestamp.
 * @property high The highest price of the asset during the time interval.
 * @property low The lowest price of the asset during the time interval.
 * @property close The price of the asset at the end of the interval.
 * @property volume The value of all instances of this asset that were traded within the interval.
 */
data class HistoricalQuote(
    val timestamp: Date,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Long?
)