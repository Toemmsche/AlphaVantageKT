package alphavantagekt.entities

import alphavantagekt.connection.Requester
import alphavantagekt.entities.history.IndicatorHistory
import alphavantagekt.entities.history.StockHistory
import alphavantagekt.entities.quote.GlobalQuote
import alphavantagekt.entities.quote.HistoricalQuote
import alphavantagekt.enums.IndicatorInterval
import alphavantagekt.enums.Scope


/**
 * The Asset subclass for a stock.
 *
 * @property symbol The stock ticker, e.g. AAPL.
 * @property latestQuote A GlobalQuote object containing all current information about the Stock
 */
class Stock(
    val symbol: String
) : Asset {

    //Current data
    var latestQuote: GlobalQuote
        get() = Requester.getGlobalQuote(symbol)
        private set(value) = Unit

    //Historical data
    override val intradayHistory: StockHistory =
        StockHistory(this, Scope.INTRADAY)
        get() : StockHistory {
            field.fetch()
            return field
        }
    override val dailyHistory: StockHistory =
        StockHistory(this, Scope.DAILY)
        get() : StockHistory {
            field.fetch()
            return field
        }
    override val weeklyHistory: StockHistory =
        StockHistory(this, Scope.WEEKLY)
        get() : StockHistory {
            field.fetch()
            return field
        }
    override val monthlyHistory: StockHistory =
        StockHistory(this, Scope.MONTHLY)
        get() :StockHistory {
            field.fetch()
            return field
        }

    override fun toString(): String {
        return symbol
    }
}