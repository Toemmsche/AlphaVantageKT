import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

fun parseAvDate(str: String): LocalDateTime {
    try {
        return LocalDateTime.parse(str, DateTimeFormatter.ofPattern(
                "yyyy-MM-dd HH:mm:ss"))
    } catch (e: DateTimeParseException) {
        return LocalDate.parse(str, DateTimeFormatter.ofPattern(
                "yyyy-MM-dd")).atStartOfDay() //without time
    }
}

fun parseZonedAvDate(str: String, timeZone: TimeZone): ZonedDateTime {
    return ZonedDateTime.of(parseAvDate(str), timeZone.toZoneId())
}

fun parseAvTime(str: String): LocalTime {
    return LocalTime.parse(str, DateTimeFormatter.ofPattern("HH:mm"))
}

fun parseAvTimeZone(str : String) : TimeZone {
    return TimeZone.getTimeZone(ZoneId.of(str).normalized())
}

fun JsonObject.firstWithSuffix(suffix: Any): String {
    val strSuffix = suffix.toString()
    return this.entries.first {
        it.key.endsWith(strSuffix)
    }.value.jsonPrimitive.content
}


operator fun <T> List<T>.component6() = this[5]
operator fun <T> List<T>.component7() = this[6]
operator fun <T> List<T>.component8() = this[7]
