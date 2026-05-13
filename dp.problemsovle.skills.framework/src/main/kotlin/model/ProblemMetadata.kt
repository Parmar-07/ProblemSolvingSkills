package dp.problemsovle.skills.framework.model

/**
 * Immutable value object capturing all metadata associated with a competitive programming problem.
 *
 * [ProblemMetadata] replaces the brittle [SolveProblems] enum pattern. Rather than hard-coding
 * problem references into an enum that must be manually updated for every new problem, each
 * [Problem] carries its own metadata inline — enabling self-registration via the [ProblemRegistry].
 *
 * ## Why an Immutable Data Class?
 * Metadata should never change after a problem is defined. Using a `data class` ensures
 * structural equality, easy copying/diffing, and natural JSON serialisation for CI pipelines.
 *
 * @property id        The canonical LeetCode (or platform) problem number. Used for deduplication
 *                     and stable ordering in the README table.
 * @property title     Human-readable problem title, e.g. `"Two Sum"`. Used in file naming and docs.
 * @property difficulty The assessed difficulty level. Defaults to [Difficulty.UNKNOWN] if unset.
 * @property tags      A set of domain [Tag]s. Supports multi-tag filtering and README column.
 * @property platform  Source platform. Defaults to [Platform.LEETCODE].
 * @property url       Optional direct URL to the problem page. Embedded in generated markdown.
 *
 * @sample dinesh.parmar.framework.core.model.ProblemMetadataSamples.basic
 */
data class ProblemMetadata(
    val id: Int,
    val title: String,
    val difficulty: Difficulty = Difficulty.UNKNOWN,
    val tags: Set<Tag> = emptySet(),
    val platform: Platform = Platform.LEETCODE,
    val url: String? = null
) {
    /**
     * Produces a filesystem-safe slug derived from the problem title.
     *
     * Converts `"Two Sum"` → `"two-sum"`, stripping all non-alphanumeric characters
     * and collapsing consecutive spaces/hyphens.
     *
     * Used as the markdown filename: `src/main/resources/problems/two-sum.md`
     */
    val slug: String
        get() = title
            .lowercase()
            .replace(Regex("[^a-z0-9\\s-]"), "")
            .trim()
            .replace(Regex("\\s+"), "-")

    /**
     * Convenience display label combining ID and title: `"1. Two Sum"`.
     */
    val displayName: String get() = "$id. $title"
}

/**
 * Enumeration of supported difficulty levels for competitive programming problems.
 */
enum class Difficulty(val label: String) {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard"),
    UNKNOWN("Unknown")
}

/**
 * Enumeration of supported source platforms.
 *
 * Adding a new platform here is sufficient to have it appear in generated docs
 * and README badges — no other changes required.
 */
enum class Platform(val displayName: String) {
    LEETCODE("LeetCode"),
    CODEFORCES("Codeforces"),
    HACKERRANK("HackerRank"),
    CUSTOM("Custom")
}

/**
 * Taxonomy of algorithmic topics.
 *
 * Tags are used for README filtering, documentation categorisation, and future CI
 * dashboard visualisation. Extend freely — the framework picks them up automatically.
 */
enum class Tag(val displayName: String) {
    ARRAY("Array"),
    HASH_MAP("HashMap"),
    STRING("String"),
    DYNAMIC_PROGRAMMING("Dynamic Programming"),
    GRAPH("Graph"),
    TREE("Tree"),
    BINARY_SEARCH("Binary Search"),
    TWO_POINTERS("Two Pointers"),
    SLIDING_WINDOW("Sliding Window"),
    STACK("Stack"),
    QUEUE("Queue"),
    LINKED_LIST("Linked List"),
    MATH("Math"),
    BIT_MANIPULATION("Bit Manipulation"),
    BACKTRACKING("Backtracking"),
    GREEDY("Greedy"),
    SORTING("Sorting"),
    RECURSION("Recursion"),
    NUMBER_THEORY("Number Theory")
}

/** Sample usages referenced by KDoc `@sample` tags. */
private object ProblemMetadataSamples {
    fun basic() {
        val meta = ProblemMetadata(
            id = 1,
            title = "Two Sum",
            difficulty = Difficulty.EASY,
            tags = setOf(Tag.ARRAY, Tag.HASH_MAP),
            url = "https://leetcode.com/problems/two-sum/"
        )
        println(meta.slug)       // "two-sum"
        println(meta.displayName) // "1. Two Sum"
    }
}
