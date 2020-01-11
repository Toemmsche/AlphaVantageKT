package main.java.kotlin.enums

enum class Scope(val ShareFormatted : String, val FXFormatted : String) {
    INTRADAY("TIME_SERIES_INTRADAY", "FX_INTRADAY"),
    DAILY("TIME_SERIES_DAILY", "FX_DAILY"),
    WEEKLY("TIME_SERIES_WEEKLY", "FX_WEEKLY"),
    MONTHLY("TIME_SERIES_MONTHLY", "FX_MONTHLY")
}