package query

import blocks.*
import blocks.Function
import javax.xml.crypto.Data
import blocks.ParameterName as PM

/**
 * Implements the builder pattern for queries to the Alpha Vantage API.
 */
class QueryBuilder {

    protected var type: QueryType = QueryType.STOCK
    protected val params = emptyMap<PM, Any>().toMutableMap()

    // Always required
    fun type(type: QueryType): QueryBuilder {
        this.type = type
        return this
    }

    fun dataType(type: DataType) : QueryBuilder {
        params[PM.DATATYPE] = type
        return this
    }

    fun function(function: Function): QueryBuilder {
        params[PM.FUNCTION] = function
        return this
    }

    fun symbol(symbol: String): QueryBuilder {
        params[PM.SYMBOL] = symbol
        return this
    }

    fun apiKey(apiKey: String): QueryBuilder {
        params[PM.APIKEY] = apiKey
        return this
    }

    // Sometimes required
    fun interval(interval: Interval): QueryBuilder {
        params[PM.INTERVAL] = interval
        return this
    }

    fun slice(slice: Slice): QueryBuilder {
        params[PM.SLICE] = slice
        return this
    }

    fun adjusted(adjusted: Boolean): QueryBuilder {
        params[PM.ADJUSTED] = adjusted
        return this
    }

    // Always optional
    fun outputSize(outputSize: OutputSize): QueryBuilder {
        params[PM.OUTPUT_SIZE] = outputSize
        return this
    }


    fun build(): Query {
        return Query(type, params)
    }
}