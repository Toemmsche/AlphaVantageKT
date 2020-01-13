package main.java.kotlin.alphavantage

import main.java.kotlin.entities.quote.ExchangeQuote
import main.java.kotlin.entities.quote.HistoricalQuote
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object Parser {

    val sdfWithTime = SimpleDateFormat("yyyy-MM-dd HH:mm:SS")
    val sdfDaysOnly = SimpleDateFormat("yyyy-MM-dd")

    fun parseHistoryHTTPResponse(response: List<String>): MutableList<HistoricalQuote> {
        val quotes = ArrayList<HistoricalQuote>();
        for (line in response) {
            parseHistoryCSVLine(line)?.also { quotes.add(it) }
        }
        return quotes
    }

    private fun parseHistoryCSVLine(line: String): HistoricalQuote? {
        if (!line.contains(",") || line.startsWith("timestamp")) return null

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
            volume = arr[5].toLong()
        } catch (e: Exception) {
        }

        return HistoricalQuote(date, open, high, low, close, volume)
    }

    fun parseExchangeRateHTTPResponse(response: List<String>): ExchangeQuote {
        lateinit var timestamp: Date
        lateinit var fromCode: String
        lateinit var toCode: String
        var rate: Double = 0.0
        var bid: Double= 0.0
        var ask: Double= 0.0

        for (line in response) {
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

}