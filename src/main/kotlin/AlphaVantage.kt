import query.DataType
import query.Function
import query.Interval
import query.OutputSize
import model.AlphaVantageFactory
import query.QueryBuilder
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun main() {
    val date = LocalDate.parse("2021-10-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay()
    println(QueryBuilder()
                    .apiKey("CSE3RJSJLAVEG0HL")
                    .function(Function.GLOBAL_QUOTE)
                    //.adjusted(true)
                    .symbol("TSLA")
                    .build()
                    .also { println(it.toUrl()) }
                    .send()
                    .also { println(it.body); AlphaVantageFactory().createGlobalQuote(it)})


    //TODO get AMD quotes
}