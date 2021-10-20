package model

import blocks.Interval
import blocks.JsonProperties
import blocks.OutputSize
import blocks.QueryType
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import parseZonedAvDate
import query.Response
import java.util.*

class AlphaVantageFactory {

    fun createStock(response: Response): Stock {
        if (response.query.type != QueryType.STOCK) {
            throw IllegalArgumentException(
                    "Alpha Vantage Query type does not match")
        }
        val metadata = response
                .json()
                .jsonObject[JsonProperties.METADATA.toString()]!!
                .jsonObject

        // map to internal object according to response pattern of Alpha Vantage
        // see https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=IBM&interval=5min&apikey=demo
        val information =
                metadata[JsonProperties.INFORMATION.toString()].toString()
        val symbol =
                metadata[JsonProperties.SYMBOL.toString()].toString()
        // We need the time zone for "last refreshed"
        val timeZone =
                TimeZone.getTimeZone(
                        metadata[JsonProperties.TIME_ZONE.toString()]
                                ?.toString())
        val lastRefreshed =
                parseZonedAvDate(
                        metadata[JsonProperties.LAST_REFRESHED.toString()].toString(),
                        timeZone)
        val interval =
                Interval.valueOf(
                        metadata[JsonProperties.INTERVAL.toString()].toString())
        val outputSize = OutputSize.valueOf(
                metadata[JsonProperties.OUTPUT_SIZE.toString()].toString())


        val history = response
                .json()
                .jsonObject
                .entries
                .first { // find history attribute
                    it.key.startsWith(JsonProperties.TIME_SERIES.toString())
                }
                .value
                .jsonObject
                .entries
                .map { createHistoricalStock(it.toPair(), timeZone) }
        return Stock(information, symbol, lastRefreshed, interval, outputSize,
                     timeZone, history)
    }

    private fun createHistoricalStock(structure: Pair<String, JsonElement>,
                                      timeZone: TimeZone): HistoricalStock {
        val identifier = structure.first
        val jsonObject = structure.second.jsonObject

        // Parse date
        val date = parseZonedAvDate(identifier, timeZone)

        // Parse data
        val open =
                jsonObject[JsonProperties.OPEN.toString()].toString().toDouble()
        val high =
                jsonObject[JsonProperties.HIGH.toString()].toString().toDouble()
        val low =
                jsonObject[JsonProperties.LOW.toString()].toString().toDouble()
        val close = jsonObject[JsonProperties.CLOSE.toString()].toString()
                .toDouble()

        val volume = jsonObject[JsonProperties.VOLUME.toString()].toString()
                .toInt()

        return HistoricalStock(date, open, high, low, close, volume)
    }
}