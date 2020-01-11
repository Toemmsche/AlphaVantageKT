import alphavantage.Requester
import entities.Currency
import entities.FX
import entities.Stock
import enums.Scope


fun main() {
    println(Currency("GBP").convertTo(Currency("USD")))
}