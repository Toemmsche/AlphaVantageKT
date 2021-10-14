package query

import exceptions.AlphaVantageException
import java.net.URL
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class Query(private val params: Map<String, String>) {

    private val ALPHA_VANTAGE_URL = "https://www.alphavantage.co/query"

    /**
     * Send out the query to the Alpha Vantage backend and wait for the response.
     * @return The response body.
     */
    fun send(): String {
        val queryUrl = URL(ALPHA_VANTAGE_URL + "?" + params
                .map { it.key + "=" + it.value }
                .joinToString("&"))
        val httpClient = HttpClient.newHttpClient()
        val httpRequest = HttpRequest
                .newBuilder(queryUrl.toURI())
                .GET()
                .build()
        val httpResponse: HttpResponse<String> =
                httpClient.send(
                        httpRequest,
                        HttpResponse.BodyHandlers.ofString(),
                )
        return when (httpResponse.statusCode()) {
            in 200 until 300 -> httpResponse.body()
            in 400 until 500 -> throw AlphaVantageException(httpResponse.body())
            else -> httpResponse.body()
        }
    }

    // TODO toStockQuote(), toFXQuote(), ... functions, maybe in constructor with serializable objects
}