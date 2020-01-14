package alphavantagekt.entities

import alphavantagekt.entities.history.History

interface Trackable {

    //Historical data
    val intradayHistory : History
    val dailyHistory : History
    val weeklyHistory : History
    val monthlyHistory : History
}