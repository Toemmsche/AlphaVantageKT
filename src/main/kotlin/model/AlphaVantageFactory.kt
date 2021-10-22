package model
import component6
import component7
import component8
import firstWithSuffix
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import model.stock.HistoricalStock
import model.stock.Stock
import parseZonedAvDate
import query.*
import java.time.ZonedDateTime
import java.util.*
import query.Function
import response.Response
import response.CsvProperties as CP
import response.JsonProperties as JP
import query.ParameterName as PM

class AlphaVantageFactory {

    fun createStock(response: Response): Stock {
        if (response.query.type != QueryType.STOCK) {
            throw IllegalArgumentException(
                    "Alpha Vantage Query type does not match")
        }
        val queryParams = response.query.params
        // Every query needs a function
        val function: Function = queryParams[PM.FUNCTION] as Function
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
                    .jsonObject[JP.METADATA.toString()]!!
                    .jsonObject

            // map to internal object according to response pattern of Alpha Vantage
            // see https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=IBM&interval=4min&apikey=demo
            val information =
                    metadata.firstWithSuffix(JP.INFORMATION.toString())
            val symbol =
                    metadata.firstWithSuffix(JP.SYMBOL.toString())
            // We need the time zone for "last refreshed"
            val timeZone =
                    TimeZone.getTimeZone(
                            metadata.firstWithSuffix(JP.TIME_ZONE.toString()))
            val lastRefreshed =
                    parseZonedAvDate(
                            metadata.firstWithSuffix(
                                    JP.LAST_REFRESHED.toString()),
                            timeZone,
                    )
            val interval = if (queryParams.containsKey(PM.INTERVAL))
                queryParams[PM.INTERVAL] as Interval else
                Interval.valueOf(
                        metadata.firstWithSuffix(JP.INTERVAL.toString()))
            val outputSize =
                    if (queryParams.containsKey(PM.OUTPUT_SIZE))
                        queryParams[PM.OUTPUT_SIZE] as OutputSize else
                        OutputSize.fromAlias(
                                metadata.firstWithSuffix(
                                        JP.OUTPUT_SIZE.toString()))

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
                JP.OPEN,
                JP.HIGH,
                JP.LOW,
                JP.CLOSE,
                JP.ADJUSTED_CLOSE,
                JP.VOLUME,
                JP.DIVIDEND_AMOUNT,
                JP.SPLIT_COEFFICIENT,
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
                volume!!.toInt(),
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
                volume!!.toInt(),
        )
    }


}