package response.stock;

enum class SearchJsonProperties(val strVal : String) {
    BEST_MATCHES("bestMatches"),
    SYMBOL("symbol"),
    NAME("name"),
    TYPE("type"),
    REGION("region"),
    MARKET_OPEN("marketOpen"),
    MARKET_CLOSE("marketClose"),
    TIMEZONE("timezone"),
    CURRENCY("currency"),
    MATCH_SCORE("matchScore");

    override fun toString() = strVal
}