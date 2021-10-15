package model

import blocks.Interval
import blocks.JsonProperties
import blocks.OutputSize
import blocks.QueryType
import kotlinx.serialization.json.jsonObject
import query.Response
import java.util.*

class Stock(response: Response) : AlphaVantageData(response) {

    val information: String
    val lastRefreshed: String
    val symbol: String
    val interval: Interval
    val outputSize: OutputSize
    val timeZone: TimeZone

    init {
        if (response.query.type != QueryType.STOCK) {
            throw IllegalArgumentException(
                    "Alpha Vantage Query type does not match")
        }
        val metadata = response
                .json()
                .jsonObject[JsonProperties.METADATA.toString()]!!
                .jsonObject

        information =
                metadata[JsonProperties.INFORMATION.toString()]!!.toString()
        symbol =
                metadata[JsonProperties.SYMBOL.toString()]!!.toString()
        lastRefreshed =
                metadata[JsonProperties.LAST_REFRESHED.toString()]!!
                        .toString()
        interval =
                Interval.valueOf(
                        metadata[JsonProperties.INTERVAL.toString()]!!
                                .toString())
        outputSize =
                OutputSize.valueOf(
                        metadata[JsonProperties.OUTPUT_SIZE.toString()]!!
                                .toString())
        timeZone =
                TimeZone.getTimeZone(
                        metadata[JsonProperties.TIME_ZONE.toString()]!!
                                .toString())
    }
}