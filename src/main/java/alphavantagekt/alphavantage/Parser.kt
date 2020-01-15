package alphavantagekt.alphavantage

import alphavantagekt.entities.quote.ExchangeQuote
import alphavantagekt.entities.quote.HistoricalQuote
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import alphavantagekt.entities.quote.IndicatorQuote
import java.net.http.HttpResponse
import java.text.ParseException

/**
 *
 */
object Parser {

    val sdfWithTime = SimpleDateFormat("yyyy-MM-dd HH:mm:SS")
    val sdfWithOutSeconds = SimpleDateFormat("yyyy-MM-dd HH:mm")
    val sdfDaysOnly = SimpleDateFormat("yyyy-MM-dd")

    val dateFormats = listOf<SimpleDateFormat>(sdfWithTime, sdfWithOutSeconds, sdfDaysOnly)

    fun parseHistoryHTTPResponse(response: HttpResponse<String>): MutableList<HistoricalQuote> {
        val lines = response.body().lines()
        val quotes = ArrayList<HistoricalQuote>()
        for (line in lines) {
            parseHistoryCSVLine(line)?.also { quotes.add(it) }
        }
        return quotes
    }

    private fun parseHistoryCSVLine(line: String): HistoricalQuote? {
        if (!line.contains(",") || line.startsWith("time")) return null

        val arr = line.split(",")

        //Only intraday quotes have an hour attached to them
        lateinit var date: Date
        try {
            date = sdfWithTime.parse(arr[0])
        } catch (e: Exception) {
            date = sdfDaysOnly.parse(arr[0])
        }

        val open = arr[1].toDouble()
        val high = arr[2].toDouble()
        val low = arr[3].toDouble()
        val close = arr[4].toDouble()

        //FX quotes and indicator quotes do not have a specified volume
        var volume: Long? = null
        try {
            //Cryptocurrency quotes also show the values in USD before volume
            if (arr.size > 6) {
                volume = arr[9].toDouble().toLong()
            } else {
                volume = arr[5].toDouble().toLong()
            }
        } catch (e: Exception) {
        }

        return HistoricalQuote(date, open, high, low, close, volume)
    }

    fun parseExchangeRateHTTPResponse(response: HttpResponse<String>): ExchangeQuote {
        val lines = response.body().lines()

        lateinit var timestamp: Date
        lateinit var fromCode: String
        lateinit var toCode: String
        var rate: Double = 0.0
        var bid: Double = 0.0
        var ask: Double = 0.0

        for (line in lines) {
            if (!line.contains(":")) continue
            val uf = line.split(":".toRegex(), 2)[1]
            when {
                line.contains("1. From_Currency Code") -> {
                    fromCode = uf.substring(uf.indexOf('"') + 1, uf.lastIndexOf('"'))
                }
                line.contains("3. To_Currency Code") -> {
                    toCode = uf.substring(uf.indexOf('"') + 1, uf.lastIndexOf('"'))
                }
                line.contains("5. Exchange Rate") -> {
                    rate = uf.substring(uf.indexOf('"') + 1, uf.lastIndexOf('"')).toDouble()
                }
                line.contains("6. Last Refreshed") -> {
                    timestamp = sdfWithTime.parse(uf.substring(uf.indexOf('"') + 1, uf.lastIndexOf('"')))
                }
                line.contains("8. Bid Price") -> {
                    bid = uf.substring(uf.indexOf('"') + 1, uf.lastIndexOf('"')).toDouble()
                }
                line.contains("9. Ask Price") -> {
                    ask = uf.substring(uf.indexOf('"') + 1, uf.lastIndexOf('"')).toDouble()
                }
            }
        }

        return ExchangeQuote(timestamp, fromCode, toCode, rate, bid, ask)
    }

    fun parseIndicatorHTTPResponse(response: HttpResponse<String>): MutableList<IndicatorQuote> {
        val lines = response.body().lines()
        val quotes = ArrayList<IndicatorQuote>()
        for (line in lines) {
            parseIndicatorCSVLine(line)?.also { quotes.add(it) }
        }
        return quotes
    }

    private fun parseIndicatorCSVLine(line: String): IndicatorQuote? {
        println(line)
        if (!line.contains(",") || line.startsWith("time")) return null

        val arr = line.split(",")

        //Only intraday quotes have an hour attached to them
        lateinit var date: Date
        for (sdf in dateFormats) {
            try {
                date = sdf.parse(arr[0])
                break;
            } catch (e: ParseException) {
            }
        }
        val value = arr[1].toDouble()
        return IndicatorQuote(date, value)
    }

}