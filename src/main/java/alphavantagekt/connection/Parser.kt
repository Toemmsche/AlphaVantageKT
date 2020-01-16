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
import kotlin.collections.HashMap

/**
 *  An object for parsing HTTP responses from the AlphaVantage API server.
 *
 * Receives HTTP responses from @see Requester and parses the content into objects.
 * Only supports the csv datatype so far.
 *
 * @property sdfWithTime A date format with the date and time.
 * @property sdfWithoutSeconds A date format with the date as well as hours and minutes.
 * @property sdfDaysOnly A date format with the date.
 * @property dateFormats Contains all date formats to be iterated through.
 */
object Parser {

    private val sdfWithTime = SimpleDateFormat("yyyy-MM-dd HH:mm:SS")
    private val sdfWithOutSeconds = SimpleDateFormat("yyyy-MM-dd HH:mm")
    private val sdfDaysOnly = SimpleDateFormat("yyyy-MM-dd")
    private val dateFormats = listOf<SimpleDateFormat>(sdfWithTime, sdfWithOutSeconds, sdfDaysOnly)

    /**
     * Parses the response of a request made to retrieve historical data about an asset.
     *
     * @param response The HTTP response received from AlphaVantage
     * @return The historical datapoints contained in the body of the response
     */
    fun parseHistoryHTTPResponse(response: HttpResponse<String>): MutableList<HistoricalQuote> {
        val lines = response.body().lines()
        val quotes = ArrayList<HistoricalQuote>()
        for (line in lines) {
            parseHistoryCSVLine(line)?.also { quotes.add(it) }
        }
        return quotes
    }

    /**
     * Parses a single csv line containing one historical datapoint for an asset.
     *
     * @param line The line to be parsed.
     * @return A custom quote containing all the information of the datapoint.
     */
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

    /**
     * Parses the response of a request made to retrieve data about the exchange rate between to currencies.
     *
     * @param response The HTTP response received from AlphaVantage.
     * @return The contents of the body wrapped in a custom quote object.
     */
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

            /*  The exchange rate request always returns json, so a bit of brute forcing is required to avoid
                the usage of a json parser, which would only be required for this specific task. */
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

    /**
     * Parses the response of a request made to retrieve data about an indicator for an underlying security.
     *
     * @param response The HTTP response received from AlphaVantage
     * @return The historical datapoints contained in the body of the response
     */
    fun parseIndicatorHTTPResponse(response: HttpResponse<String>): MutableList<IndicatorQuote> {
        val lines = response.body().lines()
        val quotes = ArrayList<IndicatorQuote>()

        //Different indicators have different amounts of information per datapoint.
        //The header is needed to filter out the obtain the attributes.
        val attributes = lines[0].split(",").toList()
        for (line in lines.subList(1, lines.size)) {
            parseIndicatorCSVLine(line, attributes)?.also { quotes.add(it) }
        }

        return quotes
    }

    /**
     * Parses a single csv line dontaining one historical datapoint for an indicator.
     *
     * @param line The line to be parsed.
     * @param attributes The names of the attributes in the appearing order.
     * @return A custom quote object containing all the information of the datapoint.
     */
    private fun parseIndicatorCSVLine(line: String, attributes : List<String>): IndicatorQuote? {
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

        //Different indicators have different amounts of information per datapoint.
        val map = HashMap<String, Double>()
        //timestamp was already parsed
        for (i in 1..attributes.lastIndex) {
            map.put(attributes[i], arr[i].toDouble())
        }

        return IndicatorQuote(date, map)
    }

}