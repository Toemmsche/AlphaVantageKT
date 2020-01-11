package exceptions

import java.lang.IllegalArgumentException

class FinanceException(message : String) : IllegalArgumentException(message) {
}