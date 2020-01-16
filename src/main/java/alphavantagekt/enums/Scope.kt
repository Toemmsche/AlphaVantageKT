package alphavantagekt.enums

/**
 * An enum for all the scopes accepted by the API for the retrieval of historical data.
 * @property stockFormatted The scope formatted for a query retrieving stock data.
 * @property FXFormatted The scope formatted for a query retrieving FX data.
 * @property cryptoFormatted The scope formatted for a query retrieving cryptoccurency data.
 */
enum class Scope(val stockFormatted: String, val FXFormatted: String, val cryptoFormatted: String) {
    INTRADAY("TIME_SERIES_INTRADAY", "FX_INTRADAY", ""),
    DAILY("TIME_SERIES_DAILY", "FX_DAILY", "DIGITAL_CURRENCY_DAILY"),
    WEEKLY("TIME_SERIES_WEEKLY", "FX_WEEKLY", "DIGITAL_CURRENCY_WEEKLY"),
    MONTHLY("TIME_SERIES_MONTHLY", "FX_MONTHLY", "DIGITAL_CURRENCY_MONTHLY")
}