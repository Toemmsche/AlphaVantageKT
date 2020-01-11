package entities

import entities.history.FXHistory
import enums.Scope

class FX(
    fromSymbol : String,
    val toSymbol : String
) : Trackable, Currency(fromSymbol) {

    //Historical data
    override val intradayHistory: FXHistory =
        FXHistory(fromSymbol, toSymbol, Scope.INTRADAY)
        get() : FXHistory {
            field.fetch()
            return field
        }
    override val dailyHistory: FXHistory =
        FXHistory(fromSymbol, toSymbol, Scope.DAILY)
        get() : FXHistory {
            field.fetch()
            return field
        }
    override val weeklyHistory: FXHistory =
        FXHistory(fromSymbol, toSymbol, Scope.WEEKLY)
        get() : FXHistory {
            field.fetch()
            return field
        }
    override val monthlyHistory: FXHistory =
        FXHistory(fromSymbol, toSymbol, Scope.MONTHLY)
        get() : FXHistory {
            field.fetch()
            return field
        }
}