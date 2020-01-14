package main.java.kotlin

import main.java.kotlin.alphavantage.Requester
import main.java.kotlin.entities.Currency

fun provideKey(key : String) {
    Requester.key = key;
}

fun main() {
    provideKey("CSE3RJSJLAVEG0HL")
    println(Currency("GBP").convertTo(Currency("USD")))
    println(Currency("CAD").convertFrom(Currency("CHF")))
}