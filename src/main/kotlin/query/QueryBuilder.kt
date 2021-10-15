package query

import blocks.*
import blocks.Function

/**
 * Implements the builder pattern for queries to the Alpha Vantage API.
 */
class QueryBuilder {

    protected var type: QueryType = QueryType.STOCK
    protected val params = emptyMap<Parameter, Any>().toMutableMap()

    // Always required
    fun type(type: QueryType): QueryBuilder {
        this.type = type
        return this
    }

    fun function(function: Function): QueryBuilder {
        params[Parameter.FUNCTION] = function
        return this
    }

    fun symbol(symbol: String): QueryBuilder {
        params[Parameter.SYMBOL] = symbol
        return this
    }

    fun apiKey(apiKey: String): QueryBuilder {
        params[Parameter.APIKEY] = apiKey
        return this
    }

    // Sometimes required
    fun interval(interval: Interval): QueryBuilder {
        params[Parameter.INTERVAL] = interval.strVal
        return this
    }

    fun slice(slice: Slice): QueryBuilder {
        params[Parameter.SLICE] = slice
        return this
    }

    fun adjusted(adjusted: Boolean): QueryBuilder {
        params[Parameter.ADJUSTED] = adjusted
        return this
    }

    // Always optional
    fun outputSize(outputSize: OutputSize): QueryBuilder {
        params[Parameter.OUTPUT_SIZE] = outputSize
        return this
    }


    fun build(): Query {
        return Query(type, params)
    }
}