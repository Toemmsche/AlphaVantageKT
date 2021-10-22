package response

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import exceptions.AlphaVantageException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import query.Query

/**
 * Encapsulates a successful response from the Alpha Vantage backend.
 * @property query The query that triggered the response.
 * @property body The body of the HTTP response.
 */
class Response(val query: Query, val body: String) {

    /**
     * @return The response body as a built-in JSON element.
     * @throws SerializationException If the body is not a valid JSON string.
     * @throws AlphaVantageException If this response indicates an Error.
     *
     */
    fun json(): JsonElement {
        val jsonElement = Json.parseToJsonElement(body)
        val errorMsgKey = "Error Message"
        if (jsonElement.jsonObject.containsKey(errorMsgKey)) {
            throw AlphaVantageException(
                    jsonElement.jsonObject[errorMsgKey].toString())
        }
        return jsonElement
    }

    /**
     * @return The response as a parsed CSV document
     */
    fun csv() = csvReader().readAllWithHeader(body)
}

