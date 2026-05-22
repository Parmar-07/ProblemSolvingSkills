package dinesh.parmar.skills.input

data class TwoSum(val array : IntArray, val target : Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TwoSum

        if (!array.contentEquals(other.array)) return false
        if (target != other.target) return false

        return true
    }

    override fun hashCode(): Int {
        var result = array.contentHashCode()
        result = 31 * result + target
        return result
    }

    override fun toString(): String {

        val sb = StringBuilder("[")
        array.forEachIndexed{ i, a ->
            sb.append(a)
            if (i != array.size-1)
            sb.append(",")
        }
        sb.append("] target = $target")

        return sb.toString()
    }
}
