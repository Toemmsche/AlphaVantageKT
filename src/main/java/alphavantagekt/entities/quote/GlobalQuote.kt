package alphavantagekt.entities.quote

import java.util.*

/**
 * A data class corresponding to the global quote for stocks defined in the ALphaVantege API Documentation.
 *
 * @property symbol The symbol of the stock.
 * @property open The price of the stock at today's market opening.
 * @property high The highest price of the stock so far today.
 * @property low The lowest price of the stock so far today.
 * @property close The current price of the stock.
 * @property volume The value of all instances of this stock that were traded this day up until now.
 * @property lastTradingDay The last day on which the stock was traded.
 * @property previousClose The price of the stock at last trading day's market closing.
 * @property change The absolute change of the  price of the stock since the previous close.
 * @property percentageChange The relative change of the price of the stock since the previous close.
 */
data class GlobalQuote(val symbol : String,
                       val open : Double,
                       val high : Double,
                       val low : Double,
                       val price : Double,
                       val volume : Long,
                       val lastTradingDay : Date,
                       val previousClose : Double,
                       val change : Double,
                       val percentageChange : Double
                       )