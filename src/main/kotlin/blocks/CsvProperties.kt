package blocks

enum class CsvProperties(val strVal: String) {
    DATE("time"),
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
