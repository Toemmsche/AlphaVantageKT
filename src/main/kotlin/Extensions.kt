import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun parseAvDate(str: String) : LocalDateTime {
    return LocalDateTime.parse(str, DateTimeFormatter.ofPattern(
            "YYYY-MM-DD HH:MM:SS"))
}

fun parseZonedAvDate(str: String, timeZone: TimeZone) : ZonedDateTime {
    return ZonedDateTime.of(parseAvDate(str), timeZone.toZoneId())
}