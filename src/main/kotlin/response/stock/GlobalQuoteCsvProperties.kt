package response.stock

enum class GlobalQuoteCsvProperties(val strVal : String) {
    SYMBOL("symbol"),
    OPEN("open"),
    HIGH("high"),
    LOW("low"),
    VOLUME("volume"),
    LATEST_TRADING_DAY("latestDay"),
    PREVIOUS_CLOSE("previousClose"),
    PRICE("price"),
    CHANGE("change"),
    CHANGE_PERCENT("changePercent");

    override fun toString() = strVal
}