package alphavantagekt.exceptions

import java.lang.IllegalArgumentException

/**
 * An exception that is thrown when an API call doesn't succeed.
 */
class FinanceException(message : String) : IllegalArgumentException(message)