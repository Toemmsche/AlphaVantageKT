package org.toemmsche.alphavantagekt.entities

import org.toemmsche.alphavantagekt.entities.history.History
import org.toemmsche.alphavantagekt.entities.history.IndicatorHistory
import org.toemmsche.alphavantagekt.enums.IndicatorInterval

/**
 * A common interface for all assets that are publicly traded.
 *
 * @property intradayHistory The historical data for the current day.
 * @property dailyHistory The historical data containing one datapoint for each day.
 * @property weeklyHistory The historical data containing one datapoint for each week.
 * @property monthlyHistory The historical data containing one datapoint for each month.
 */
interface Asset {


    //Historical data
    val intradayHistory : History
    val dailyHistory : History
    val weeklyHistory : History
    val monthlyHistory : History

    /**
     * @param symbol The symbol of the requested incdicator, e.g. SMA (Simple Moving Average)
     * @param interval The interval between datapoints of the indicator.
     * @param params The parameters used for the Indicator. For some indicators, additional parameters are required.
     * @return An IndicatorHistory object containing the requested datapoints.
     */
    fun getIndicator(symbol : String, interval : IndicatorInterval, params: Map<String, String>) : IndicatorHistory =
        IndicatorHistory(symbol, this, interval, params)

    /**
     * @return The short string representation of this asset.
     */
    abstract override fun toString() : String
}