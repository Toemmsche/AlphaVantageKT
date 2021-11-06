package model.stock

import API_KEY
import exceptions.ApiLimitExceedException
import model.AlphaVantageFactory
import org.junit.jupiter.api.Assumptions.assumeFalse
import query.DataType
import query.Function
import query.QueryBuilder
import query.QueryType
import kotlin.test.Test

internal class GlobalQuoteTest {

    val factory = AlphaVantageFactory()

    @Test
    fun testJson() {
        var apiLimitedExceeded = false;
        try {
            val bestMatches = factory.createGlobalQuote(
                    QueryBuilder()
                            .type(QueryType.STOCK)
                            .apiKey(API_KEY)
                            .function(Function.GLOBAL_QUOTE)
                            .symbol("AMD")
                            .dataType(DataType.JSON)
                            .build()
                            .send())
        } catch (e: ApiLimitExceedException) {
            apiLimitedExceeded = true
        }
        assumeFalse(apiLimitedExceeded)
    }

    @Test
    fun testCsv() {
        var apiLimitedExceeded = false;
        try {
            val bestMatches = factory.createGlobalQuote(
                    QueryBuilder()
                            .type(QueryType.STOCK)
                            .apiKey(API_KEY)
                            .function(Function.GLOBAL_QUOTE)
                            .symbol("TSLA")
                            .dataType(DataType.CSV)
                            .build()
                            .send())
        } catch (e: ApiLimitExceedException) {
            apiLimitedExceeded = true
        }
        assumeFalse(apiLimitedExceeded)
    }
}

