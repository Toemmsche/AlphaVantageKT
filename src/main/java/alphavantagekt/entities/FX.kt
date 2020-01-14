package alphavantagekt.entities

import alphavantagekt.entities.history.FXHistory
import alphavantagekt.enums.Scope


class FX(
    val fromSymbol : String,
    val toSymbol : String
) : Trackable {

    //Historical data
    override val intradayHistory: FXHistory =
        FXHistory(this, Scope.INTRADAY)
        get() : FXHistory {
            field.fetch()
            return field
        }
    override val dailyHistory: FXHistory =
        FXHistory(this, Scope.DAILY)
        get() : FXHistory {
            field.fetch()
            return field
        }
    override val weeklyHistory: FXHistory =
        FXHistory(this, Scope.WEEKLY)
        get() : FXHistory {
            field.fetch()
            return field
        }
    override val monthlyHistory: FXHistory =
        FXHistory(this, Scope.MONTHLY)
        get() : FXHistory {
            field.fetch()
            return field
        }
}