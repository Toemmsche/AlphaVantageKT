package blocks

enum class JsonProperties(val strVal: String) {
    METADATA("Meta Data"),
    LAST_REFRESHED("3. Last Refreshed"),
    SYMBOL("2. Symbol"),
    INFORMATION("1. information"),
    INTERVAL("4. Interval"),
    OUTPUT_SIZE("5. Output Size"),
    TIME_ZONE("6. Time Zone"),
    TIME_SERIES("Time Series"),
    OPEN("1. open"),
    HIGH("2. high"),
    LOW("3. low"),
    CLOSE("4. close"),
    VOLUME("5. volume");

    override fun toString() = strVal
}