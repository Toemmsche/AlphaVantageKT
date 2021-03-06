package org.toemmsche.alphavantagekt.entities.history

import org.toemmsche.alphavantagekt.entities.Asset
import org.toemmsche.alphavantagekt.entities.quote.HistoricalQuote
import org.toemmsche.alphavantagekt.enums.Interval
import org.toemmsche.alphavantagekt.enums.Scope

/**
 * A common superclass for all history classes associated with assets (this excludes indicators).
 *
 * Wraps the datapoints retrieved from the API and allows updates as well as modficiation of data.
 *
 * @property scope The interval between datapoints specified during the API call.
 * @property data The historical datapoints received from the API wrapped in custom quotes.
 * @property underlyingAsset The asset associated with this history. Can be a stock, FX pair or cryptocurrency.
 * @property initialised Signals if the specified history has been retrieved from the API yet.
 */
abstract class History(
    val scope: Scope,
    val data: MutableList<HistoricalQuote> = ArrayList<HistoricalQuote>()
) : MutableList<HistoricalQuote> by data {

    abstract val underlyingAsset: Asset
    var initialised = false

    /**
     * Retrieves the historical data specified by the scope and the underlying asset from the API if the object
     * currently doesn't contain any data.
     */
    fun fetch() {
        if (!initialised) {
            update()
            initialised = true
        }
    }

    /**
     * Updates the historical data specified by the scope and the underlying asset with an API call.
     *
     * @return A history object containing the newly retrieved data (most often this object itself).
     */
    abstract fun update(): History

    /**
     * Updates the historical data with a given interval if the scope is intraday, otherwise throws an Exception.
     *
     * @param interval The interval between intraday datapoints
     * @return A history object containing the newly retrieved data (most often this object itself).
     */
    abstract fun update(interval : Interval) : History

    /**
     * @return The metadata and datapoints in a readable format.
     */
    abstract override fun toString(): String
}