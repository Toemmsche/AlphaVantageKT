package model.stock

import java.time.LocalTime
import java.util.*

// TODO convert type and region to enum
data class SearchMatch(
        val symbol: String,
        val name: String,
        val type: String,
        val region : String,
        val marketOpen: LocalTime,
        val marketClose: LocalTime,
        val timeZone: TimeZone,
        val currency: Currency,
        val matchScore: Float,
        ) {
}