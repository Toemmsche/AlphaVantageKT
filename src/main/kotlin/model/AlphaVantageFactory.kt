package model

import blocks.Interval
import blocks.JsonProperties
import blocks.OutputSize
import blocks.QueryType
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import query.Response
import java.util.*

class AlphaVantageFactory {

    fun createStock(response: Response) {
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
        val lastRefreshed =
                metadata[JsonProperties.LAST_REFRESHED.toString()].toString()
        val interval =
                Interval.valueOf(
                        metadata[JsonProperties.INTERVAL.toString()].toString())
        val outputSize = OutputSize.valueOf(
                metadata[JsonProperties.OUTPUT_SIZE.toString()].toString())

        val timeZone =
                TimeZone.getTimeZone(
                        metadata[JsonProperties.TIME_ZONE.toString()]
                                ?.toString())
        val history = response
                .json()
                .jsonObject
                .entries
                .first { it.key.startsWith(JsonProperties.TIME_SERIES.toString())} // find history attribute
                .value
                .jsonObject
                .entries
                .map { createHistoricalStock(it.toPair()) }
    }

    private fun createHistoricalStock(toPair: Pair<String, JsonElement>) {
        // TODO
    }
}