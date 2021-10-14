package blocks

class Slice(val year: Int, val month: Int) {

    init {
        // Check date bounds
        // Only the last two years worth of data can be retrieved
        assert(year in 1..2)
        assert(month in 1..12)
    }

    override fun toString(): String {
        return "year${year}month${month}"
    }


}