package dp.problemsovle.skills.framework.registry

import dp.problemsovle.skills.framework.model.Problem
import dp.problemsovle.skills.framework.model.Tag

/**
 * Central registry for all [Problem] instances in the framework.
 *
 * [ProblemRegistry] eliminates the anti-pattern of maintaining a manually-updated
 * `HashMap<SolveProblems, Problem<*, *>>` in `ProblemTestRule`. Instead, each problem
 * registers itself at construction time, and the registry acts as the single look-up
 * surface for runners, documentation generators, and the README updater.
 *
 * ## Scalability
 * Backed by a [LinkedHashMap] to preserve insertion order (which mirrors declaration order
 * in `Main.kt`). Supports 1000+ problems with O(1) lookup by ID.
 *
 * ## Thread Safety
 * [ProblemRegistry] is intended for single-threaded use during test execution and
 * documentation generation. If parallel test runners are introduced, synchronise access
 * externally or migrate to `ConcurrentHashMap`.
 *
 * ## Usage
 * ```kotlin
 * // Register once at startup
 * ProblemRegistry.register(TwoSum())
 * ProblemRegistry.register(PalindromeNumber())
 *
 * // Retrieve by ID
 * val problem = ProblemRegistry.getById(1)
 *
 * // Run all registered problems
 * ProblemRegistry.all().forEach { it.runTests() }
 * ```
 */
object ProblemRegistry {

    private val store: LinkedHashMap<Int, Problem<*, *>> = LinkedHashMap()

    /**
     * Registers a [Problem] instance, keyed by its [Problem.metadata.id].
     *
     * Duplicate IDs overwrite the previous entry with a warning — this allows local
     * overrides during development but is flagged clearly in logs.
     *
     * @param problem The problem instance to register.
     */
    fun register(problem: Problem<*, *>) {
        val id = problem.metadata.id
        if (store.containsKey(id)) {
            System.err.println(
                "⚠ ProblemRegistry: Problem with id=$id ('${store[id]?.metadata?.title}') " +
                "overwritten by '${problem.metadata.title}'."
            )
        }
        store[id] = problem
    }

    /**
     * Registers multiple [Problem] instances in a single call.
     *
     * Syntactic sugar for bulk registration at application startup.
     *
     * @param problems Vararg list of problem instances.
     */
    fun registerAll(vararg problems: Problem<*, *>) {
        problems.forEach { register(it) }
    }

    /**
     * Returns the problem registered under the given [id], or `null` if not found.
     *
     * @param id The platform problem ID (e.g. `1` for LeetCode "Two Sum").
     * @return The registered [Problem], or `null`.
     */
    fun getById(id: Int): Problem<*, *>? = store[id]

    /**
     * Returns all registered problems in insertion order.
     *
     * Used by the documentation generator and README updater to iterate over
     * the full solved-problems catalogue.
     *
     * @return Immutable [List] of all registered [Problem] instances.
     */
    fun all(): List<Problem<*, *>> = store.values.toList()

    /**
     * Returns all problems tagged with any of the given [tags].
     *
     * Useful for generating per-category README sections or CI test suites.
     *
     * @param tags One or more [Tag] values to filter by.
     * @return Problems whose `metadata.tags` intersects with [tags].
     */
    fun filterByTags(vararg tags: Tag): List<Problem<*, *>> {
        val tagSet = tags.toSet()
        return all().filter { it.metadata.tags.intersect(tagSet).isNotEmpty() }
    }

    /**
     * Returns the total count of registered problems.
     */
    val size: Int get() = store.size

    /**
     * Clears all registered problems. Intended for use in tests only.
     */
    fun clear() = store.clear()
}
