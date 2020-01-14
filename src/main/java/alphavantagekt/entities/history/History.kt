package main.java.kotlin.entities.history

import main.java.kotlin.entities.Trackable
import main.java.kotlin.entities.quote.HistoricalQuote
import main.java.kotlin.enums.Scope

abstract class History(
    val scope: Scope,
    var data: MutableList<HistoricalQuote> = ArrayList<HistoricalQuote>()
) : MutableList<HistoricalQuote> by data {

    abstract val underlyingAsset: Trackable

    var initialised = false
    fun fetch() {
        if (!initialised && update()) {
            initialised = true
        }
    }

    abstract fun update(): Boolean

    abstract override fun toString(): String
}