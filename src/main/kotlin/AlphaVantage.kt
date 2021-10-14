import query.QueryBuilder

fun main() {
    println(QueryBuilder()
            .apiKey("ABCD")
            .build()
            .send())
    //TODO get AMD quotes
}