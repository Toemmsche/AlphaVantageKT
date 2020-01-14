package alphavantagekt.entities

import alphavantagekt.entities.history.IndicatorHistory
import alphavantagekt.entities.history.ShareHistory
import alphavantagekt.enums.IndicatorInterval
import alphavantagekt.enums.Scope


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

    fun getIndicator(symbol : String, interval : IndicatorInterval, params: Map<String, String>) : IndicatorHistory =
        IndicatorHistory(symbol, this, interval, params)
}