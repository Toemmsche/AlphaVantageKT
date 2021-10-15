package blocks

enum class JsonProperties(val strVal: String) {
    METADATA("metadata"),
    LAST_REFRESHED("Last Refreshed"),
    SYMBOL("symbol"),
    INFORMATION("information"),
    INTERVAL("interval"),
    OUTPUT_SIZE("Output Size"),
    TIME_ZONE("Time Zone");

    override fun toString() = strVal
}