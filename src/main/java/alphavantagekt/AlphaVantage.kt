package alphavantagekt

import alphavantagekt.connection.Requester
import alphavantagekt.entities.Stock

/**
 * A method for providing the Requester with an API key.
 * Can be retrieved from https://www.alphavantage.co/support/#api-key
 *
 * @param key The API key in a string format,
 * @return If the API key is valid.
 */
fun provideKey(key : String) : Boolean {
   return Requester.testKey(key)
}

fun main() {
    println(provideKey("CSE3RJSJLAVEG0HL"))
    //Requester.key = "CSE3RJSJLAVEG0HL"
    println(Stock("AMD").latestQuote)
}