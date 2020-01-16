package alphavantagekt.exceptions

import java.lang.Exception
import java.lang.RuntimeException

/**
 * An exception that is thrown when an API call is made before an API key is provided.
 * Can be retrieved from https://www.alphavantage.co/support/#api-key
 */
class MissingApiKeyException : RuntimeException("No API key supplied. Get your API key from https://www.alphavantage.co/support/#api-key " +
        "and call provideKey(key) before proceeding.") {
}