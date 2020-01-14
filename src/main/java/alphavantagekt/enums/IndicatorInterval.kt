package kotlin.enums

enum class IndicatorInterval(val formatted : String) {
    ONE_MINUTE("1min"),
    FIVE_MINUTES("5min"),
    FIFTEEN_MINUTES("15min"),
    THIRTY_MINUTES("30min"),
    ONE_HOUR("60min"),
    DAIL("daily"),
    WEEKLY("weekly"),
    MONTHLY("monthly")
}