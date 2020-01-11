package entities

import entities.history.History

interface Trackable {

    //Historical data
    abstract val intradayHistory : History
    abstract val dailyHistory : History
    abstract val weeklyHistory : History
    abstract val monthlyHistory : History
}