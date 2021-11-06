package response

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import exceptions.AlphaVantageException
import exceptions.ApiLimitExceedException

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import query.Query
import java.net.http.HttpHeaders
import java.net.http.HttpResponse

/**
 * Encapsulates a successful response from the Alpha Vantage backend.
 * @property query The query that triggered the response.
 * @property httpResponse The HTTP response.
 */
class Response(val query: Query,
               private val httpResponse: HttpResponse<String>) {
    val body
        get() = httpResponse.body()

    /**
     * @return The response body as a built-in JSON element.
     * @throws SerializationException If the body is not a valid JSON string.
     * @throws AlphaVantageException If this response indicates an Error.
     *
     */
    fun json(): JsonElement = Json.parseToJsonElement(body)


    /**
     * @return If this response is a valid JSON response.
     */
    fun isJson() : Boolean {
        var isJson = false
        httpResponse.headers().firstValue("Content-Type").ifPresent {
            isJson = it == "application/json"
        }
        return isJson
    }

    /**
     * @throws AlphaVantageException If something went wrong.
     * @throws ApiLimitExceedException If the (free) API call limit has been
     *     exceeded.
     */
    fun checkValidity() {
        if (isJson()) {
            val jsonObject = Json.parseToJsonElement(
                    httpResponse.body()).jsonObject
            val errorMsgKey = "Error Message"
            val limitExceededKey = "Note"
            if (jsonObject.containsKey(errorMsgKey)) {
                throw AlphaVantageException(
                        jsonObject[errorMsgKey].toString())
            } else if (jsonObject.containsKey(limitExceededKey)) {
                throw ApiLimitExceedException(
                        jsonObject[limitExceededKey].toString())
            }
        }
    }

    /**
     * @return The response as a parsed CSV document
     */
    fun csv() = csvReader().readAllWithHeader(body)
}

