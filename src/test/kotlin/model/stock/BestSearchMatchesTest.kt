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

internal class BestSearchMatchesTest {

    val factory = AlphaVantageFactory()

    @Test
    fun testJson() {
        var apiLimitExceeded = false
        try {
            val bestMatches = factory.createSearchMatches(
                    QueryBuilder()
                            .type(QueryType.STOCK)
                            .apiKey(API_KEY)
                            .function(Function.SYMBOL_SEARCH)
                            .keywords("Game")
                            .dataType(DataType.JSON)
                            .build()
                            .send())
        } catch (e: ApiLimitExceedException) {
            // OK
            apiLimitExceeded = true
        }
        assumeFalse(apiLimitExceeded)
    }

    @Test
    fun testCsv() {

        var apiLimitExceeded = false
        try {
            val bestMatches = factory.createSearchMatches(
                    QueryBuilder()
                            .type(QueryType.STOCK)
                            .apiKey(API_KEY)
                            .function(Function.SYMBOL_SEARCH)
                            .keywords("Technology")
                            .dataType(DataType.CSV)
                            .build()
                            .send())
        } catch (e: ApiLimitExceedException) {
            // OK
            apiLimitExceeded = true
        }

        assumeFalse(apiLimitExceeded)
    }
}

