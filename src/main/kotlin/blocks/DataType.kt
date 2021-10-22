package blocks

import kotlin.ParameterName

/**
 * An enum for all options for the data type of an API response
 *
 * @property strVal The data type formatted as a String for the API query.
 */
enum class DataType(val strVal: String)  {
    CSV("csv"),
    JSON("json");

    override fun toString() = strVal
}