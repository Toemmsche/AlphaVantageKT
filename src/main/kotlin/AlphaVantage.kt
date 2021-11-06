import model.AlphaVantageFactory
import query.Function
import query.QueryBuilder
import query.QueryType
import java.time.ZoneId
import java.util.*

fun main() {
    println(TimeZone.getTimeZone(ZoneId.of("UTC+4").normalized()))
    println(QueryBuilder()
                    .type(QueryType.STOCK)
                    .apiKey("CSE3RJSJLAVEG0HL")
                    .function(Function.SYMBOL_SEARCH)
                    .keywords("Game")
                    //.adjusted(true)
                    .build()
                    .also { println(it.toUrl()) }
                    .send()
                    .also { println(it.body); println(
                            AlphaVantageFactory().createSearchMatches(
                                    it)[0].timeZone.toString())
                    })


    //TODO get AMD quotes
}