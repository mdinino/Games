package dinino.marc.games

/**
 * Implementing classes must consider equals/hashcode behavior.
 * Data classes automatically meet the requirement/
 */
interface DataType {
    override fun equals(other: Any?): Boolean
    override fun hashCode(): Int
}