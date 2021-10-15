package blocks

import kotlin.ParameterName

/**
 * An enum for all options for the output size of an API response.
 *
 * @property strVal The output size value formatted as a String for the API
 *                  query.
 */
enum class OutputSize(val strVal: String) {
    COMPACT("compact"),
    FULL("full");
}