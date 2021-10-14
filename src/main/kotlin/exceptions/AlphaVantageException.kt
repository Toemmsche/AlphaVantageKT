package exceptions

import java.lang.RuntimeException

class AlphaVantageException(override val message : String) : RuntimeException()