package main.java.kotlin.entities

import main.java.kotlin.entities.history.ShareHistory
import main.java.kotlin.enums.Scope

/**
 * Represents a stock, whose underlying will be requested from the API on-demond, NOT on creation.
 */
class Stock(
    symbol: String
) : Trackable {

    //Historical data
    override val intradayHistory: ShareHistory =
        ShareHistory(symbol, Scope.INTRADAY)
        get() : ShareHistory {
            field.fetch()
            return field
        }
    override val dailyHistory: ShareHistory =
        ShareHistory(symbol, Scope.DAILY)
        get() : ShareHistory {
            field.fetch()
            return field
        }
    override val weeklyHistory: ShareHistory =
        ShareHistory(symbol, Scope.WEEKLY)
        get() : ShareHistory {
            field.fetch()
            return field
        }
    override val monthlyHistory: ShareHistory =
        ShareHistory(symbol, Scope.MONTHLY)
        get() :ShareHistory {
            field.fetch()
            return field
        }

}