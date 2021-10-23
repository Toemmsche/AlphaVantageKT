package model

import component6
import component7
import component8
import firstWithSuffix
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import model.stock.GlobalQuote
import model.stock.HistoricalStock
import model.stock.Stock
import parseAvDate
import parseZonedAvDate
import query.*
import response.Response
import java.time.ZonedDateTime
import java.util.*
import query.ParameterName as PM
import response.CsvProperties as CP
import response.GlobalQuoteJsonProperties as GJP
import response.StockJsonProperties as SJP

class AlphaVantageFactory {

    fun createStock(response: Response): Stock {
        if (response.query.type != QueryType.STOCK) {
            throw IllegalArgumentException(
                    "Alpha Vantage Query type does not match")
        }
        val queryParams = response.query.params
        // Every query needs a function
        val function = queryParams[PM.FUNCTION] as query.Function
        if (queryParams[PM.DATATYPE] == DataType.CSV) {
            val interval = if (queryParams.containsKey(PM.INTERVAL))
                queryParams[PM.INTERVAL] as Interval else null
            val outputSize =
                    if (queryParams.containsKey(PM.OUTPUT_SIZE))
                        queryParams[PM.OUTPUT_SIZE] as OutputSize else null
            val timeZone = TimeZone.getTimeZone("US/Eastern")
            val symbol = queryParams[PM.SYMBOL] as String
            val csv = response.csv().toMutableList()
            val history = csv.map { createHistoricalStock(it, timeZone) }
            return Stock(function, null, symbol, ZonedDateTime.now(), interval,
                         outputSize, timeZone,
                         history)
        } else {
            val metadata = response
                    .json()
                    .jsonObject[SJP.METADATA.toString()]!!
                    .jsonObject

            // map to internal object according to response pattern of Alpha Vantage
            // see https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=IBM&interval=4min&apikey=demo
            val information =
                    metadata.firstWithSuffix(SJP.INFORMATION.toString())
            val symbol =
                    metadata.firstWithSuffix(SJP.SYMBOL.toString())
            // We need the time zone for "last refreshed"
            val timeZone =
                    TimeZone.getTimeZone(
                            metadata.firstWithSuffix(SJP.TIME_ZONE.toString()))
            val lastRefreshed =
                    parseZonedAvDate(
                            metadata.firstWithSuffix(
                                    SJP.LAST_REFRESHED.toString()),
                            timeZone,
                    )
            val interval = if (queryParams.containsKey(PM.INTERVAL))
                queryParams[PM.INTERVAL] as Interval else
                Interval.valueOf(
                        metadata.firstWithSuffix(SJP.INTERVAL.toString()))
            val outputSize =
                    if (queryParams.containsKey(PM.OUTPUT_SIZE))
                        queryParams[PM.OUTPUT_SIZE] as OutputSize else
                        OutputSize.fromAlias(
                                metadata.firstWithSuffix(
                                        SJP.OUTPUT_SIZE.toString()))

            val history = response
                    .json()
                    .jsonObject
                    .entries.elementAt(1)
                    .value
                    .jsonObject
                    .entries
                    .map { createHistoricalStock(it.toPair(), timeZone) }


            return Stock(function, information, symbol, lastRefreshed, interval,
                         outputSize,
                         timeZone, history)
        }
    }

    fun createHistoricalStock(structure: Pair<String, JsonElement>,
                              timeZone: TimeZone): HistoricalStock {
        val identifier = structure.first
        val jsonObject = structure.second.jsonObject

        // Parse date
        val date = parseZonedAvDate(identifier, timeZone)

        // Parse data
        val (
                open,
                high,
                low,
                close,
                adjustedClose,
                volume,
                dividendAmount,
                splitCoefficient,
        ) = listOf(
                SJP.OPEN,
                SJP.HIGH,
                SJP.LOW,
                SJP.CLOSE,
                SJP.ADJUSTED_CLOSE,
                SJP.VOLUME,
                SJP.DIVIDEND_AMOUNT,
                SJP.SPLIT_COEFFICIENT,
        ).map mapper@{
            try {
                return@mapper jsonObject.firstWithSuffix(it.toString())
                        .toDouble()
            } catch (e: NoSuchElementException) {
                return@mapper null
            }
        }

        return HistoricalStock(
                date,
                open!!,
                high!!,
                low!!,
                close!!,
                adjustedClose,
                volume!!.toInt(),
                dividendAmount,
                splitCoefficient,
        )
    }

    fun createHistoricalStock(fields: Map<String, String>,
                              timeZone: TimeZone): HistoricalStock {
        // Parse date
        val date = parseZonedAvDate(fields[CP.DATE.toString()]!!, timeZone)
        val (
                open,
                high,
                low,
                close,
                adjustedClose,
                volume,
                dividendAmount,
                splitCoefficient,
        ) = listOf(
                CP.OPEN,
                CP.HIGH,
                CP.LOW,
                CP.CLOSE,
                CP.ADJUSTED_CLOSE,
                CP.VOLUME,
                CP.DIVIDEND_AMOUNT,
                CP.SPLIT_COEFFICIENT,
        ).map { fields[it.toString()]?.toDouble() }
        return HistoricalStock(
                date,
                open!!,
                high!!,
                low!!,
                close!!,
                adjustedClose,
                volume!!.toInt(),
                dividendAmount,
                splitCoefficient,
        )
    }

    fun createGlobalQuote(response: Response): GlobalQuote {
        val jsonObject = response
                .json()
                .jsonObject[GJP.GLOBAL_QUOTE.toString()]!!
                .jsonObject
        val latestTradingDay = parseAvDate(jsonObject.firstWithSuffix(
                GJP.LATEST_TRADING_DAY.toString())).toLocalDate()

        val symbol = jsonObject.firstWithSuffix(GJP.SYMBOL.toString())
        val changePercent = jsonObject
                .firstWithSuffix(GJP.CHANGE_PERCENT.toString())
                .dropLast(1)
                .toDouble()
        val (
                open,
                high,
                low,
                price,
                volume,
                previousClose,
                change,
        ) = listOf(
                GJP.OPEN,
                GJP.HIGH,
                GJP.LOW,
                GJP.PRICE,
                GJP.VOLUME,
                GJP.PREVIOUS_CLOSE,
                GJP.CHANGE
        ).map { jsonObject.firstWithSuffix(it.toString()).toDouble() }
        return GlobalQuote(
                symbol,
                open,
                high,
                low,
                price,
                volume.toInt(),
                latestTradingDay,
                previousClose,
                change,
                changePercent,
        )
    }


}