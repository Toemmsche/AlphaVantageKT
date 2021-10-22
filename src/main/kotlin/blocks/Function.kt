package blocks

/**
 * An enum for the functions accepted by the AlphaVantage API for the retrieval of data.
 * @property strVal The function value formatted as a string for the API query.
 */
enum class Function(val strVal: String) {
    TIME_SERIES_INTRADAY("TIME_SERIES_INTRADAY"),
    TIME_SERIES_INTRADAY_EXTENDED("TIME_SERIES_INTRADAY_EXTENDED"),
    TIME_SERIES_DAILY("TIME_SERIES_DAILY"),
    TIME_SERIES_DAILY_ADJUSTED("TIME_SERIES_DAILY_ADJUSTED"),
    TIME_SERIES_WEEKLY("TIME_SERIES_WEEKLY"),
    TIME_SERIES_WEEKLY_ADJUSTED("TIME_SERIES_WEEKLY_ADJUSTED"),
    TIME_SERIES_MONTHLY("TIME_SERIES_MONTHLY"),
    TIME_SERIES_MONTHLY_ADJUSTED("TIME_SERIES_MONTHLY_ADJUSTED"),
    GLOBAL_QUOTE("GLOBAL_QUOTE"),
    SYMBOL_SEARCH("SYMBOL_SEARCH"),
    OVERVIEW("OVERVIEW");

    override fun toString() = strVal
}

