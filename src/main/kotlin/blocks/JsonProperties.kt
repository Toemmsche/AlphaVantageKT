package blocks

enum class JsonProperties(val strVal: String) {
    METADATA("Meta Data"),
    LAST_REFRESHED("Last Refreshed"),
    SYMBOL("Symbol"),
    INFORMATION("Information"),
    INTERVAL("Interval"),
    OUTPUT_SIZE("Output Size"),
    TIME_ZONE("Time Zone"),
    TIME_SERIES("Time Series"),
    OPEN("open"),
    HIGH("high"),
    LOW("low"),
    CLOSE("close"),
    VOLUME("volume"),
    ADJUSTED_CLOSE("adjusted close"),
    DIVIDEND_AMOUNT("dividend amount"),
    SPLIT_COEFFICIENT("split coefficient");

    override fun toString() = strVal
}