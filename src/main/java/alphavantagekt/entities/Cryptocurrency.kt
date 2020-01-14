package alphavantagekt.entities

import alphavantagekt.entities.history.CryptoHistory
import alphavantagekt.enums.Scope
import java.lang.UnsupportedOperationException
import java.util.*

class Cryptocurrency(symbol : String,
                     val market : String) : Trackable, Currency(symbol) {

    constructor(symbol : String, currency : Currency) : this(symbol, currency.symbol){}

    //Historical data
    override val intradayHistory: CryptoHistory
        get() : CryptoHistory {
            throw UnsupportedOperationException()
        }
    override val dailyHistory:CryptoHistory =
        CryptoHistory(this, Scope.DAILY)
        get() : CryptoHistory {
            field.fetch()
            return field
        }
    override val weeklyHistory: CryptoHistory =
        CryptoHistory(this, Scope.WEEKLY )
        get() : CryptoHistory {
            field.fetch()
            return field
        }
    override val monthlyHistory: CryptoHistory =
        CryptoHistory(this, Scope.MONTHLY)
        get() : CryptoHistory {
            field.fetch()
            return field
        }
}