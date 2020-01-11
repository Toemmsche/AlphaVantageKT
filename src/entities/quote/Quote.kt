package entities.quote

import java.util.*

abstract class Quote(val timestamp : Date) {

    abstract override fun toString() : String
}