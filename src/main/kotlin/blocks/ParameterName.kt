package blocks

/**
 * Parameter names for the Alpha Vantage API.
 *
 * @property strVal The name of the parameter inside the query URL as a string.
 */
enum class ParameterName(val strVal: String) {
    FUNCTION("function"),
    INTERVAL("interval"),
    OUTPUT_SIZE("outputsize"),
    ADJUSTED("adjusted"),
    DATATYPE("datatype"),
    SYMBOL("symbol"),
    APIKEY("apikey"),
    SLICE("slice");

    override fun toString() = this.strVal
}