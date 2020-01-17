package org.toemmsche.alphavantagekt.exceptions

import java.lang.IllegalArgumentException
import java.lang.RuntimeException

/**
 * An exception that is thrown when an API call doesn't succeed.
 */
class FinanceException(message : String) : RuntimeException(message)