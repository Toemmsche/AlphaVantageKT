package org.toemmsche.alphavantagekt.enums

/**
 * An enum for all intervals accepted by the API for the retrieval of indicator data.
 *
 * @property formatted The interval formatted for the URL query.
 */
enum class IndicatorInterval(val formatted : String) {
    ONE_MINUTE("1min"),
    FIVE_MINUTES("5min"),
    FIFTEEN_MINUTES("15min"),
    THIRTY_MINUTES("30min"),
    ONE_HOUR("60min"),
    DAILY("daily"),
    WEEKLY("weekly"),
    MONTHLY("monthly")
}