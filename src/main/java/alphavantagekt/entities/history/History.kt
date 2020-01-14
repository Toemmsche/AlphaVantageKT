package alphavantagekt.entities.history

import alphavantagekt.entities.Trackable
import alphavantagekt.entities.quote.HistoricalQuote
import alphavantagekt.enums.Interval
import alphavantagekt.enums.Scope


abstract class History(
    val scope: Scope,
    var data: MutableList<HistoricalQuote> = ArrayList<HistoricalQuote>()
) : MutableList<HistoricalQuote> by data {


    abstract val underlyingAsset: Trackable

    var initialised = false
    fun fetch() {
        if (!initialised) {
            update()
            initialised = true
        }
    }

    abstract fun update(): History

    abstract fun update(interval : Interval) : History

    abstract override fun toString(): String
}