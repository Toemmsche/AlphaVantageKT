package alphavantage

import entities.quote.HistoricalQuote
import entities.quote.Quote
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object Parser {

    fun parseHistoryHTTPResponse(response : List<String>) : MutableList<HistoricalQuote> {
        val quotes = ArrayList<Quote>();
        for (line in response) {
            parseHistoryCSVLine(line)?.also { quotes.add(it) }
        }
        return quotes
    }

    private fun parseHistoryCSVLine(line: String): HistoricalQuote? {
        if (!line.contains(",") || line.startsWith("timestamp")) return null

        val arr =  line.split(",")

        //Only intraday quotes have an hour attached to them
        val sdfWithTime = SimpleDateFormat("yyyy-MM-dd HH:mm:SS")
        val sdfDaysOnly = SimpleDateFormat("yyyy-MM-dd")
        lateinit var date : Date
        try {
            date = sdfWithTime.parse(arr[0])
        } catch(e : Exception) {
            date = sdfDaysOnly.parse(arr[0])
        }

        val open = arr[1].toDouble()
        val high = arr[2].toDouble()
        val low = arr[3].toDouble()
        val close = arr[4].toDouble()

        //FX quotes and indicator quotes do not have a specified volume
        var volume : Long? = null
        try {
            volume = arr[5].toLong()
        } catch(e : Exception) {}

        return HistoricalQuote(date, open, high, low, close, volume)
    }

    fun parseExchangeRateHTTPResponse(response : String) : Double {
        json
    }

}