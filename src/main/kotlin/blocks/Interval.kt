package blocks

/**
 * An enum for all intervals accepted by the API for retrieval of data.
 *
 * @property strVal The interval value formatted as a String for the API query.
 */
enum class Interval(val strVal: String)  {
    ONE_MINUTE("1min"),
    FIVE_MINUTES("5min"),
    FIFTEEN_MINUTES("15min"),
    THIRTY_MINUTES("30min"),
    SIXTY_MINUTES("60min");
}