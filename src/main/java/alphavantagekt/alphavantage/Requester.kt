package alphavantagekt.alphavantage

import alphavantagekt.entities.quote.ExchangeQuote
import alphavantagekt.entities.quote.HistoricalQuote

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import alphavantagekt.entities.quote.IndicatorQuote
import alphavantagekt.enums.IndicatorInterval
import alphavantagekt.enums.Interval
import alphavantagekt.enums.Scope
import alphavantagekt.exceptions.FinanceException

/**
 * An object for communication with the API server.
 *
 * Sends HTTP requests to the Alphavantage server and passes the responses to @see Parser.
 *
 * @property key The API key required for performing API requests. Can be retrieved from https://www.alphavantage.co/support/#api-key
 * @property host The host URL address of the Alphavantage API.
 */
object Requester {

    lateinit var key: String
    val host = "https://www.alphavantage.co"

    /**
     * Sends a HTTPRequest to the Alphavantage server.
     *
     * @param query The query formatted as URL Parameters which will be appended to the host address.
     * @throws FinanceException When the HTTP request was unsuccessful.
     * @return The Response sent by Alphavantage.
     */
    @Throws(FinanceException::class)
    private fun request(query: String): HttpResponse<String> {
        val client = HttpClient.newHttpClient()
        val request = HttpRequest
            .newBuilder(URI(host + query))
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        if (response.statusCode() == 200 && !response.body().contains("Error Message")) {
            return response
        } else if (response.statusCode() != 200) {
            throw FinanceException("Unsuccessful HTTP Request [$query]")
        } else {
            throw FinanceException("Unsuccessful API Request [$query]: ${response.body()}")
        }
    }

    /**
     * Returns historical data of a stock.
     *
     * @param symbol The ticker of the stock, e.g. AAPL.
     * @param scope The interval between datapoints.
     * @param interval The interval between datapoints if the scope is intraday.
     * @return The datapoints in the format of historical quotes.
     *
     */
    fun getShareData(symbol: String, scope: Scope, interval: Interval?): MutableList<HistoricalQuote> {
        val url = "/query?function=${scope.shareFormatted}&symbol=${symbol}&interval=${interval?.formatted
            ?: "5min"}&apikey=$key&datatype=csv"
        return Parser.parseHistoryHTTPResponse(request(url))
    }

    /**
     * Returns historical data of a Forex Exchange pair (FX).
     *
     * @param fromSymbol The currency symbol (three letters) of the currency to be converted from.
     * @param toSymbol The currency symbol (three letters) of the curency to be converted to.
     * @param scope The interval between datapoints.
     * @param interval The interval between datapoints if the scope is intraday.
     * @return The datapoints in the format of historical quotes.
     */
    fun getFXData(
        fromSymbol: String,
        toSymbol: String,
        scope: Scope,
        interval: Interval?
    ): MutableList<HistoricalQuote> {
        val url =
            "/query?function=${scope.FXFormatted}&from_symbol=${fromSymbol}&to_symbol=$toSymbol&interval=${interval?.formatted
                ?: "5min"}&apikey=$key&datatype=csv"

        return Parser.parseHistoryHTTPResponse(request(url))
    }

    /**
     * Returns the exchange rate between to specified currencies.
     *
     * @param fromCurrency The currency symbol (three letters) of the currency to be converted from.
     * @param toCurrency The currency symbol (three letters) of the curency to be converted to.
     * @return An ExchangeQuote Object containing all the relevant and current data of the currency pair
     */
    fun getExchangeRate(fromCurrency: String, toCurrency: String): ExchangeQuote {
        val url =
            "/query?function=CURRENCY_EXCHANGE_RATE&from_currency=${fromCurrency}&to_currency=$toCurrency&apikey=$key"
        return Parser.parseExchangeRateHTTPResponse(request(url))
    }

    /**
     * Returns historical data of an indicator for an underlying stock.
     *
     * @param symbol The indicator symbol, e.g. SMA (Simple Moving Average).
     * @param stockSymbol The ticker of the underlying stock, e.g. AAPL.
     * @param interval The interval between datapoints.
     * @param params A map of parameters for the api call, some of which might be required for certain indicators
     */
    fun getIndicatorData(
        symbol: String,
        stockSymbol: String,
        interval: IndicatorInterval,
        params: Map<String, String>
    ): MutableList<IndicatorQuote> {
        val url =
            "/query?function=$symbol&symbol=$stockSymbol&interval=${interval.formatted}&${mapToQueryString(params)}&apikey=$key&datatype=csv"
        return Parser.parseIndicatorHTTPResponse(request(url))
    }

    /**
     * Returns historical data of a cryptocurrency.
     *
     * @param symbol The crytopcurrency symbol, e.g. BTC
     * @param market The currency code of the trade market. Prices will be given for this market only.
     * @param scope The interval between datapoints.
     * @returns The datapoints in the format of historical quotes.
     */
    fun getCryptoData(symbol: String, market: String, scope: Scope): MutableList<HistoricalQuote> {
        val url = "/query?function=${scope.cryptoFormatted}&symbol=${symbol}&market=$market&apikey=$key&datatype=csv"
        return Parser.parseHistoryHTTPResponse(request(url))
    }

    /**
     * A helper function to convert String maps to URL syntax.
     *
     * @param map The String map to be converted
     * @return The contents of the map in URl query syntax
     */
    private fun mapToQueryString(map: Map<String, String>): String {
        val sb = StringBuilder()
        for (entry in map.entries) {
            sb.append("${entry.key}=${entry.value}&")
        }
        return sb.toString().dropLast(1)
    }
}