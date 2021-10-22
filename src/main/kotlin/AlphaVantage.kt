import blocks.DataType
import blocks.Function
import blocks.Interval
import blocks.OutputSize
import model.AlphaVantageFactory
import query.QueryBuilder
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main() {
    val date = LocalDate.parse("2021-10-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay()
    println(QueryBuilder()
                    .apiKey("CSE3RJSJLAVEG0HL")
                    .function(Function.TIME_SERIES_WEEKLY)
                    .interval(Interval.ONE_MINUTE)
                    .outputSize(OutputSize.FULL)
                    .dataType(DataType.JSON)
                    .adjusted(true)
                    //.adjusted(true)
                    .symbol("TSLA")
                    .build()
                    .also { println(it.toUrl()) }
                    .send()

                    .also { println(it.body); AlphaVantageFactory().createStock(it)})


    //TODO get AMD quotes
}