package alphavantagekt.exceptions

import java.lang.Exception
import java.lang.RuntimeException

class MissingApiKeyException : RuntimeException("No Api key supplied. Call provideKey(key) before proceeding.") {
}