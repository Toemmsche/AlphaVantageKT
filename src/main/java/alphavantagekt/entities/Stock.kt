package main.java.kotlin.entities

import main.java.kotlin.entities.history.ShareHistory
import main.java.kotlin.enums.Scope

/**
 * Represents a stock, whose underlying will be requested from the API on-demond, NOT on creation.
 */
class Stock(
    val symbol: String
) : Trackable {

    //Historical data
    override val intradayHistory: ShareHistory =
        ShareHistory(this, Scope.INTRADAY)
        get() : ShareHistory {
            field.fetch()
            return field
        }
    override val dailyHistory: ShareHistory =
        ShareHistory(this, Scope.DAILY)
        get() : ShareHistory {
            field.fetch()
            return field
        }
    override val weeklyHistory: ShareHistory =
        ShareHistory(this, Scope.WEEKLY)
        get() : ShareHistory {
            field.fetch()
            return field
        }
    override val monthlyHistory: ShareHistory =
        ShareHistory(this, Scope.MONTHLY)
        get() :ShareHistory {
            field.fetch()
            return field
        }

}