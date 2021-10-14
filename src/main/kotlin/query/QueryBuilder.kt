package query

import blocks.Function
import blocks.Interval
import blocks.OutputSize
import blocks.Slice

/**
 * Implements the builder pattern for queries to the Alpha Vantage API.
 */
class QueryBuilder {

    private val params = emptyMap<String, String>().toMutableMap()


    // TODO Encapsulate params

    // Always required
    fun function(function: Function): QueryBuilder {
        params["function"] = function.strVal
        return this
    }

    fun symbol(symbol: String): QueryBuilder {
        params["symbol"] = symbol
        return this
    }

    fun apiKey(apiKey: String): QueryBuilder {
        params["apiKey"] = apiKey
        return this
    }

    // Sometimes required
    fun interval(interval: Interval): QueryBuilder {
        params["interval"] = interval.strVal
        return this
    }

    fun slice(slice: Slice): QueryBuilder {
        params["slice"] = slice.toString()
        return this
    }

    fun adjusted(adjusted: Boolean) : QueryBuilder {
        params["adjusted"] = adjusted.toString()
        return this
    }

    // Always optional
    fun outputSize(outputSize: OutputSize) : QueryBuilder {
        params["outputsize"] = outputSize.strVal
        return this
    }


    fun build() : Query {
        return Query(params)
    }
}