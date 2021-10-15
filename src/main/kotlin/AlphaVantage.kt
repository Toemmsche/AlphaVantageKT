import blocks.Function
import query.QueryBuilder

fun main() {
    println(QueryBuilder()
                    .apiKey("CSE3RJSJLAVEG0HL")
                    .function(Function.GLOBAL_QUOTE)
                    .symbol("AMD")
                    .build()
                    .send()
                    .body)
    //TODO get AMD quotes
}