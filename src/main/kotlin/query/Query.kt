package query

import blocks.ParameterName
import blocks.QueryType
import exceptions.AlphaVantageException
import java.net.URL
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class Query(val type: QueryType, val params: Map<ParameterName, Any>) {


    private val ALPHA_VANTAGE_URL = "https://www.alphavantage.co/query"

    /**
     * Send out the query to the Alpha Vantage backend and wait for the response.
     * @return The response body.
     */
    fun send(): Response {
        val queryUrl = toUrl()
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
            in 200 until 300 -> Response(this, httpResponse.body())
            in 400 until 500 -> throw AlphaVantageException(httpResponse.body())
            else -> Response(this, httpResponse.body())
        }
    }

    fun toUrl() = URL("$ALPHA_VANTAGE_URL?${
        params
                .map { it.key.toString() + "=" + it.value.toString() }
                .joinToString("&")
    }")


    // TODO toStockQuote(), toFXQuote(), ... functions, maybe in constructor with serializable objects
}