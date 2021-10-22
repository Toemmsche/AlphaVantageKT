import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

fun parseAvDate(str: String): LocalDateTime {
    try {
        return LocalDateTime.parse(str, DateTimeFormatter.ofPattern(
                "yyyy-MM-dd HH:mm:ss"))
    } catch(e : DateTimeParseException) {
        return LocalDate.parse(str, DateTimeFormatter.ofPattern(
                "yyyy-MM-dd")).atStartOfDay() //without time
    }
}

fun parseZonedAvDate(str: String, timeZone: TimeZone): ZonedDateTime {
    return ZonedDateTime.of(parseAvDate(str), timeZone.toZoneId())
}

fun JsonObject.firstWithSuffix(suffix: String): String {
    return this.entries.first { it.key.endsWith(suffix) }.value.jsonPrimitive.content
}


operator fun <T> List<T>.component6() = this[5]
operator fun <T> List<T>.component7() = this[6]
operator fun <T> List<T>.component8() = this[7]
