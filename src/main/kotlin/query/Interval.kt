package query

import java.time.Duration

/**
 * An enum for all intervals accepted by the API for retrieval of data.
 *
 * @property strVal The interval value formatted as a String for the API query.
 */
enum class Interval(val strVal: String, val value: Duration)  {
    ONE_MINUTE("1min", Duration.ofMinutes(1)),
    FIVE_MINUTES("5min", Duration.ofMinutes(5)),
    FIFTEEN_MINUTES("15min", Duration.ofMinutes(15)),
    THIRTY_MINUTES("30min", Duration.ofMinutes(30)),
    SIXTY_MINUTES("60min", Duration.ofMinutes(60));

    override fun toString() = strVal
}