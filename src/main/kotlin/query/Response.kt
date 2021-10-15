package query

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

/**
 * Encapsulates a successful response from the Alpha Vantage backend.
 * @property query The query that triggered the response.
 * @property body The body of the HTTP response.
 */
class Response(val query: Query, val body: String) {

    /**
     * @return The response body as a built-in JSON element.
     * @throws SerializationException If the body is not a valid JSON string.
     *
     */
    fun json() : JsonElement {
        return Json.parseToJsonElement(body)
    }
}