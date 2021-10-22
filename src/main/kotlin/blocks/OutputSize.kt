package blocks

/**
 * An enum for all options for the output size of an API response.
 *
 * @property strVal The output size value formatted as a String for the API
 *                  query.
 */
enum class OutputSize(val strVal: String, val aliases: Set<String>) {
    COMPACT("compact", setOf("Compact")),
    FULL("full", setOf("Full size"));

    override fun toString() = strVal

    companion object {
        fun fromAlias(alias: String): OutputSize {
            return OutputSize.values().first { it.aliases.contains(alias) }
        }
    }
}