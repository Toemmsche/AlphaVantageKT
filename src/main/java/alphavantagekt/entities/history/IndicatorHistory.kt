package alphavantagekt.entities.history

import alphavantagekt.connection.Requester
import alphavantagekt.entities.Asset

import alphavantagekt.entities.quote.IndicatorQuote
import alphavantagekt.enums.IndicatorInterval

/**
 * A class containing data of an indicator for an underlying security. This class is NOT a subclass of History,
 * but offers a similar interface
 *
 * @property symbol The abbrevation for the indicator, e.g. SMA (Simple Moving Average)
 * @property underlyingSecurity The underlying security of the indicator.
 * @property interval The interval between datapoints of the indicator.
 * @property data The historical indicator data wrapped in custom quote objects.
 */
class IndicatorHistory(
    val symbol: String,
    val underlyingSecurity: Asset,
    val interval: IndicatorInterval,
    var data: MutableList<IndicatorQuote> = ArrayList<IndicatorQuote>()
) : MutableList<IndicatorQuote> by data {

    /**
     * Initializes the object with the given parameters.
     *
     * @param params The parameters used for the Indicator. For some indicators, additional parameters are required.
     */
    constructor(symbol: String, underlyingSecurity: Asset, interval: IndicatorInterval, params: Map<String, String>) : this(
        symbol,
        underlyingSecurity,
        interval
    ) {
        update(params)
    }

    /**
     * @return The metadata and datapoints in a readable format.
     */
    override fun toString() : String {
        val sb = StringBuilder()
        sb.append("indicator: $symbol, security=$underlyingSecurity, interval=${interval.formatted}\n")
        for (q in data) {
            sb.append("$q\n")
        }
        sb.dropLast(2)
        return sb.toString()
    }

    /**
     * Updates the data of the indicator with the specified parameters
     * as well as the security and interval specified at instantiation.
     *
     *  @param params The parameters used for the Indicator. For some indicators, additional parameters are required.
     *  @return An IndicatorHistory object containing the newly retrieved data (most often this object itself).
     */
    fun update(params: Map<String, String>): IndicatorHistory{
        data = Requester.getIndicatorData(symbol, underlyingSecurity.toString(), interval, params)
        return this
    }

}