package model

import component6
import component7
import component8
import firstWithSuffix
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import model.stock.*
import parseAvDate
import parseAvPercentage
import parseAvTime
import parseAvTimeZone
import parseZonedAvDate
import query.*
import query.Function
import response.Response
import java.time.ZonedDateTime
import java.util.*
import kotlin.IllegalArgumentException
import kotlin.Pair
import kotlin.String
import query.ParameterName as PM
import response.stock.GlobalQuoteCsvProperties as GCP
import response.stock.GlobalQuoteJsonProperties as GJP
import response.stock.SearchCsvProperties as SECP
import response.stock.SearchJsonProperties as SEJP
import response.stock.StockCsvProperties as SCP
import response.stock.StockJsonProperties as SJP

class AlphaVantageFactory {

    fun createStock(response: Response): Stock {
        if (response.query.type != QueryType.STOCK) {
            throw IllegalArgumentException(
                    "Alpha Vantage query type does not match")
        }
        val queryParams = response.query.params
        // Every query needs a function
        val function = queryParams[PM.FUNCTION] as query.Function
        if (!response.isJson()) {
            val interval = if (queryParams.containsKey(PM.INTERVAL))
                queryParams[PM.INTERVAL] as Interval else null
            val outputSize =
                    if (queryParams.containsKey(PM.OUTPUT_SIZE))
                        queryParams[PM.OUTPUT_SIZE] as OutputSize else null
            val timeZone = parseAvTimeZone("US/Eastern")
            val symbol = queryParams[PM.SYMBOL] as String
            val csv = response.csv().toMutableList()
            val history = csv.map { createHistoricalStock(it, timeZone) }
            return Stock(
                    function,
                    null,
                    symbol,
                    ZonedDateTime.now(),
                    interval,
                    outputSize,
                    timeZone,
                    history,
            )
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
                    parseAvTimeZone(
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
        val date = parseZonedAvDate(fields[SCP.DATE.toString()]!!, timeZone)
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
                SCP.OPEN,
                SCP.HIGH,
                SCP.LOW,
                SCP.CLOSE,
                SCP.ADJUSTED_CLOSE,
                SCP.VOLUME,
                SCP.DIVIDEND_AMOUNT,
                SCP.SPLIT_COEFFICIENT,
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
        if (response.query.type != QueryType.STOCK ||
                response.query.params[PM.FUNCTION] != Function.GLOBAL_QUOTE) {
            throw IllegalArgumentException(
                    "Alpha Vantage query type does not match")
        }
        if (response.isJson()) {
            val jsonObject = response
                    .json()
                    .jsonObject[GJP.GLOBAL_QUOTE.toString()]!!
                    .jsonObject
            val latestTradingDay = parseAvDate(jsonObject.firstWithSuffix(
                    GJP.LATEST_TRADING_DAY.toString())).toLocalDate()

            val symbol = jsonObject.firstWithSuffix(GJP.SYMBOL.toString())
            val changePercent = parseAvPercentage(
                    jsonObject.firstWithSuffix(GJP.CHANGE_PERCENT.toString()))
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
        } else {
            val csv = response.csv().first()
            val latestTradingDay = parseAvDate(
                    csv[GCP.LATEST_TRADING_DAY.toString()]!!).toLocalDate()

            val symbol = csv[GCP.SYMBOL.toString()]!!
            val changePercent =
                    parseAvPercentage(csv[GCP.CHANGE_PERCENT.toString()]!!)
            val (
                    open,
                    high,
                    low,
                    price,
                    volume,
                    previousClose,
                    change,
            ) = listOf(
                    GCP.OPEN,
                    GCP.HIGH,
                    GCP.LOW,
                    GCP.PRICE,
                    GCP.VOLUME,
                    GCP.PREVIOUS_CLOSE,
                    GCP.CHANGE
            ).map { csv[it.toString()]!!.toDouble() }
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

    fun createSearchMatches(response: Response): BestSearchMatches {
        if (response.query.type != QueryType.STOCK ||
                response.query.params[PM.FUNCTION] != Function.SYMBOL_SEARCH) {
            throw IllegalArgumentException(
                    "Alpha Vantage query type does not match")
        }
        if (response.isJson()) {
            return response
                    .json()
                    .jsonObject[SEJP.BEST_MATCHES.toString()]!!
                    .jsonArray
                    .map {
                        val jsonObject = it.jsonObject
                        val marketOpen = parseAvTime(
                                jsonObject.firstWithSuffix(SECP.MARKET_OPEN))
                        val marketClose = parseAvTime(
                                jsonObject.firstWithSuffix(SECP.MARKET_CLOSE))
                        val currency =
                                Currency.getInstance(
                                        jsonObject.firstWithSuffix(
                                                SECP.CURRENCY))
                        val matchScore =
                                jsonObject.firstWithSuffix(SEJP.MATCH_SCORE)
                                        .toDouble()
                        val timeZone = parseAvTimeZone(
                                jsonObject.firstWithSuffix(SEJP.TIMEZONE))
                        val (
                                symbol,
                                name,
                                type,
                                region,
                        ) = listOf(
                                SEJP.SYMBOL,
                                SEJP.NAME,
                                SEJP.TYPE,
                                SEJP.REGION,
                        ).map { jsonObject.firstWithSuffix(it.toString()) }
                        SearchMatch(
                                symbol,
                                name,
                                type,
                                region,
                                marketOpen,
                                marketClose,
                                timeZone,
                                currency,
                                matchScore,
                        )
                    }
                    .toCollection(BestSearchMatches())
        } else {
            return response
                    .csv()
                    .map { row ->
                        val marketOpen = parseAvTime(
                                row[SECP.MARKET_OPEN.toString()]!!)
                        val marketClose = parseAvTime(
                                row[SECP.MARKET_CLOSE.toString()]!!)
                        val currency =
                                Currency.getInstance(
                                        row[SECP.CURRENCY.toString()]!!)
                        val matchScore =
                                row[SECP.MATCH_SCORE.toString()]!!
                                        .toDouble()
                        val timeZone = parseAvTimeZone(
                                row[SECP.TIMEZONE.toString()]!!)
                        val (
                                symbol,
                                name,
                                type,
                                region,
                        ) = listOf(
                                SECP.SYMBOL,
                                SECP.NAME,
                                SECP.TYPE,
                                SECP.REGION,
                        ).map { row[it.toString()]!! }
                        SearchMatch(
                                symbol,
                                name,
                                type,
                                region,
                                marketOpen,
                                marketClose,
                                timeZone,
                                currency,
                                matchScore,
                        )
                    }
                    .toCollection(BestSearchMatches())
        }

    }


}