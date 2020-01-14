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

object Requester {

    lateinit var key: String
    val host = "https://www.alphavantage.co"

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

    fun getShareData(symbol: String, scope: Scope, interval: Interval?): MutableList<HistoricalQuote> {
        val url = "/query?function=${scope.shareFormatted}&symbol=${symbol}&interval=${interval?.formatted
            ?: "5min"}&apikey=$key&datatype=csv"
        return Parser.parseHistoryHTTPResponse(request(url))
    }

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

    fun getExchangeRate(fromCurrency: String, toCurrency: String): ExchangeQuote {
        val url =
            "/query?function=CURRENCY_EXCHANGE_RATE&from_currency=${fromCurrency}&to_currency=$toCurrency&apikey=$key"
        return Parser.parseExchangeRateHTTPResponse(request(url))
    }

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

    private fun mapToQueryString(map: Map<String, String>): String {
        val sb = StringBuilder()
        for (entry in map.entries) {
            sb.append("${entry.key}=${entry.value}&")
        }
        return sb.toString().dropLast(1)
    }

    fun getCryptoData(symbol: String, market: String, scope: Scope): MutableList<HistoricalQuote> {
        val url = "/query?function=${scope.cryptoFormatted}&symbol=${symbol}&market=$market&apikey=$key&datatype=csv"
        return Parser.parseHistoryHTTPResponse(request(url))
    }
}