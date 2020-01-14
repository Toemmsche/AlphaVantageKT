package main.java.kotlin.alphavantage

import main.java.kotlin.entities.quote.ExchangeQuote
import main.java.kotlin.entities.quote.HistoricalQuote
import main.java.kotlin.enums.Scope
import main.java.kotlin.enums.Interval
import main.java.kotlin.exceptions.FinanceException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

object Requester {

    lateinit var key: String
    val host = "https://www.alphavantage.co"

    private fun request(query: String): HttpResponse<String> {
        val client = HttpClient.newHttpClient()
        val request = HttpRequest
            .newBuilder(URI(host + query))
            .build()
        return client.send(request, HttpResponse.BodyHandlers.ofString())
    }

    fun getShareData(symbol: String, scope: Scope, interval: Interval?): MutableList<HistoricalQuote> {
        val url = "/query?function=${scope.ShareFormatted}&symbol=${symbol}&interval=${interval?.formatted
            ?: "5min"}&apikey=$key&datatype=csv"
        val response = request(url)
        if (response.statusCode() == 200 && !response.body().contains("Error Message")) {
            return Parser.parseHistoryHTTPResponse(
                response
                    .body()
                    .lines()
            )
        } else if (response.statusCode() != 200) {
            throw FinanceException("Unsuccessful HTTP Request [$url]")
        } else {
            throw FinanceException("Unsuccessful API Request [$url]: ${response.body()}")
        }
    }

    fun getFXData(fromSymbol: String, toSymbol: String, scope: Scope, interval: Interval?): MutableList<HistoricalQuote> {
        val url =
            "/query?function=${scope.FXFormatted}&from_symbol=${fromSymbol}&to_symbol=$toSymbol&interval=${interval?.formatted
                ?: "5min"}&apikey=$key&datatype=csv"
        val response = request(url)
        if (response.statusCode() == 200 && !response.body().contains("Error Message")) {
            return Parser.parseHistoryHTTPResponse(
                response
                    .body()
                    .lines()
            )
        } else if (response.statusCode() != 200) {
            throw FinanceException("Unsuccessful HTTP Request [$url]")
        } else {
            throw FinanceException("Unsuccessful API Request [$url]: ${response.body()}")
        }
    }

    fun getExchangeRate(fromCurrency: String, toCurrency: String): ExchangeQuote {
        return Parser.parseExchangeRateHTTPResponse(
            request("/query?function=CURRENCY_EXCHANGE_RATE&from_currency=${fromCurrency}&to_currency=$toCurrency&apikey=$key")
                .body()
                .lines()
        )
    }

}