package response.stock

// TODO add parse() function as parameter
enum class GlobalQuoteJsonProperties(val strVal: String) {
    SYMBOL("symbol"),
    OPEN("open"),
    HIGH("high"),
    LOW("low"),
    VOLUME("volume"),
    LATEST_TRADING_DAY("latest trading day"),
    PREVIOUS_CLOSE("previous close"),
    PRICE("price"),
    CHANGE("change"),
    CHANGE_PERCENT("change percent"),
    GLOBAL_QUOTE("Global Quote");

    override fun toString() = strVal
}