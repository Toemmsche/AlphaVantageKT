package entities.history

import entities.quote.HistoricalQuote
import entities.quote.Quote
import enums.Scope

abstract class History(val scope : Scope,
              var data : MutableList<HistoricalQuote> = ArrayList<HistoricalQuote>()
) : MutableList<HistoricalQuote> by data{

    var initialised = false

    fun fetch() {
        if (!initialised) {
            update()
            initialised = true
        }
    }

    abstract fun update()

    abstract override fun toString() : String
}