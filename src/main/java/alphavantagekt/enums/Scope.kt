package main.java.kotlin.enums

enum class Scope(val shareFormatted: String, val FXFormatted: String, cryptoFormatted: String) {
    INTRADAY("TIME_SERIES_INTRADAY", "FX_INTRADAY", ""),
    DAILY("TIME_SERIES_DAILY", "FX_DAILY", "DIGITAL_CURRENCY_DAILY"),
    WEEKLY("TIME_SERIES_WEEKLY", "FX_WEEKLY", "DIGITAL_CURRENCY_WEEKLY"),
    MONTHLY("TIME_SERIES_MONTHLY", "FX_MONTHLY", "DIGITAL_CURRENCY_MONTHLY")
}