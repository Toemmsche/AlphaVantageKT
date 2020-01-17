package org.toemmsche.alphavantagekt

import org.toemmsche.alphavantagekt.connection.Requester
import org.toemmsche.alphavantagekt.entities.FX
import org.toemmsche.alphavantagekt.enums.IndicatorInterval
import java.util.*

/**
 * A method for providing the Requester with an API key.
 * Can be retrieved from https://www.alphavantage.co/support/#api-key
 *
 * @param key The API key in a string format,
 * @return If the API key is valid.
 */
fun provideKey(key : String) : Boolean {
   return Requester.testKey(key)
}

fun main() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    println(provideKey("CSE3RJSJLAVEG0HL"))
    //Requester.key = "CSE3RJSJLAVEG0HL"
    //println(Stock("AMD").latestQuote)
    FX("EUR", "USD").getIndicator("MAMA", IndicatorInterval.FIFTEEN_MINUTES, mapOf("series_type" to "5")).also { println(it) }
}