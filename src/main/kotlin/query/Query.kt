package query

import ALPHA_VANTAGE_URL
import com.sun.net.httpserver.HttpContext
import exceptions.AlphaVantageException
import exceptions.ApiLimitExceedException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import response.Response
import java.net.URL
import java.net.http.HttpClient
import java.net.http.HttpHeaders
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class Query(val type: QueryType, val params: Map<ParameterName, Any>) {


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
            in 400 until 500 -> throw AlphaVantageException(httpResponse.body())
            else -> {
                val response = Response(this, httpResponse)
                response.checkValidity()
                response
            }
        }
    }

    fun toUrl() = URL(ALPHA_VANTAGE_URL + "?" + params
            .map { it.key.toString() + "=" + it.value.toString() }
            .joinToString("&"))


    // TODO toStockQuote(), toFXQuote(), ... functions, maybe in constructor with serializable objects
}