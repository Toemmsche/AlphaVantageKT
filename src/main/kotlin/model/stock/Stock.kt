package model.stock

import model.AlphaVantageData
import query.Function
import query.Interval
import query.OutputSize
import java.time.ZonedDateTime
import java.util.*

/**
 * A stock ticker associated with historical price data.
 *
 *
 * @property function The API time series function dictating the interval of
 *     historical data
 * @property information Summary of the retrieved stock data
 * @property symbol The stock ticker symbol
 * @property lastRefreshed The instant the data was last refreshed
 * @property interval Time between individual data points
 * @property outputSize Whether the full output is provided
 * @property timeZone The timezone used to index the historical price data
 * @property history The historical price data
 */
class Stock(
        val function: Function,
        val information: String?,
        val symbol: String,
        val lastRefreshed: ZonedDateTime,
        val interval: Interval?, // not present in every response
        val outputSize: OutputSize?, // not present in every response
        val timeZone: TimeZone,
        val history: List<HistoricalStock>
) : AlphaVantageData() {


}