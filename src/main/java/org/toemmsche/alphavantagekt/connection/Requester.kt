package org.toemmsche.alphavantagekt.connection

import org.toemmsche.alphavantagekt.entities.quote.ExchangeQuote
import org.toemmsche.alphavantagekt.entities.quote.GlobalQuote
import org.toemmsche.alphavantagekt.entities.quote.HistoricalQuote

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import org.toemmsche.alphavantagekt.entities.quote.IndicatorQuote
import org.toemmsche.alphavantagekt.enums.IndicatorInterval
import org.toemmsche.alphavantagekt.enums.Interval
import org.toemmsche.alphavantagekt.enums.Scope
import org.toemmsche.alphavantagekt.exceptions.FinanceException
import org.toemmsche.alphavantagekt.exceptions.MissingApiKeyException
import java.lang.Exception

/**
 * An object for communication with the Alphavantage API server.
 *
 * Sends HTTP requests to the Alphavantage server and passes the responses to @see Parser.
 *
 * @property key The API key required for performing API requests.
 *              Can be retrieved from https://www.alphavantage.co/support/#api-key
 * @property host The host URL address of the Alphavantage API.
 */
object Requester {

    lateinit var key: String
    private val host = "https://www.alphavantage.co"

    /**
     * Sends a HTTPRequest to the Alphavantage server.
     *
     * @param query The query formatted as URL Parameters which will be appended to the host address.
     * @return The Response sent by Alphavantage.
     *
     * @throws FinanceException When the HTTP request was unsuccessful.
     * @throws MissingApiKeyException If no key is provided at the time of the request.
     */
    @Throws(FinanceException::class, MissingApiKeyException::class)
    private fun request(query: String): HttpResponse<String> {
        if (key.isNullOrBlank()) throw MissingApiKeyException()
        val client = HttpClient.newHttpClient()
        val request = HttpRequest
            .newBuilder(URI(host + query))
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        if (response.statusCode() == 200 && !response.body().contains("Error Message")) {
            return response
        } else if (response.statusCode() != 200) {
            //Request was not understood by the server or the request was bad
            throw FinanceException("Unsuccessful HTTP Request [$query]")
        } else {
            //The request could not be interpreted by the server.
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
        val query = "/query?function=${scope.stockFormatted}&symbol=${symbol}&interval=${interval?.formatted
            ?: "5min"}&apikey=$key&datatype=csv"
        return Parser.parseHistoryHTTPResponse(request(query))
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
        val query =
            "/query?function=${scope.FXFormatted}&from_symbol=${fromSymbol}&to_symbol=$toSymbol&interval=${interval?.formatted
                ?: "5min"}&apikey=$key&datatype=csv"

        return Parser.parseHistoryHTTPResponse(request(query))
    }

    /**
     * Returns the exchange rate between to specified currencies.
     *
     * @param fromCurrency The currency symbol (three letters) of the currency to be converted from.
     * @param toCurrency The currency symbol (three letters) of the curency to be converted to.
     * @return An ExchangeQuote Object containing all the relevant and current data of the currency pair
     */
    fun getExchangeRate(fromCurrency: String, toCurrency: String): ExchangeQuote {
        val query =
            "/query?function=CURRENCY_EXCHANGE_RATE&from_currency=${fromCurrency}&to_currency=$toCurrency&apikey=$key"
        return Parser.parseExchangeRateHTTPResponse(request(query))
    }

    /**
     * Returns historical data of an indicator for an underlying security.
     *
     * @param symbol The indicator symbol, e.g. SMA (Simple Moving Average).
     * @param securitySymbol The ticker of the underlying security, e.g. AAPL, USDEUR or BTC
     * @param interval The interval between datapoints.
     * @param params A map of parameters for the api call, some of which might be required for certain indicators
     */
    fun getIndicatorData(
        symbol: String,
        securitySymbol: String,
        interval: IndicatorInterval,
        params: Map<String, String>
    ): MutableList<IndicatorQuote> {
        val query =
            "/query?function=$symbol&symbol=$securitySymbol&interval=${interval.formatted}&${mapToQueryString(params)}&apikey=$key&datatype=csv"
        return Parser.parseIndicatorHTTPResponse(request(query))
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
        val query = "/query?function=${scope.cryptoFormatted}&symbol=${symbol}&market=$market&apikey=$key&datatype=csv"
        return Parser.parseHistoryHTTPResponse(request(query))
    }

    /**
     * @param symbol The stock ticker
     * @return A custom quote object containing current data for the stock.
     */
    fun getGlobalQuote(symbol: String): GlobalQuote {
        val query = "/query?function=GLOBAL_QUOTE&symbol=${symbol}&apikey=$key&datatype=csv"
        return Parser.parseGlobalQuoteHTTPResponse(request(query))
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

    /**
     * A method to test an API key for validity.
     *
     * @param key The API key candidate to be tested.
     * @return If the API key is valid, i.e. if the test request succeeded.
     */
    fun testKey(keyCandidate: String): Boolean {
        this.key = keyCandidate
        try {
            val response =
                request("/query?function=TIME_SERIES_INTRADAY&symbol=AMD&interval=5min&apikey=$key")
            if (response.body().contains("Meta Data")
                && !response.body().contains("the parameter apikey is invalid or missing")
            ) {
                return true
            } else {
                key = ""
                return false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            key = ""
            return false
        }
    }

}