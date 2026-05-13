package dp.problemsovle.skills.framework.result

import dp.problemsovle.skills.framework.model.Example

/**
 * Sealed class hierarchy representing the outcome of executing a single test case.
 *
 * Using a sealed class instead of throwing [AssertionError] directly achieves:
 * - All test cases run even when early ones fail (no short-circuit)
 * - Structured data available for documentation generation
 * - Clean separation between execution logic and result reporting
 * - Easy exhaustive handling via `when` expressions
 *
 * ## Hierarchy
 * ```
 * TestResult<I, O>
 * ├── Passed   — algorithm returned expected value
 * ├── Failed   — algorithm returned wrong value
 * └── Errored  — algorithm threw an exception
 * ```
 *
 * @param I The input type of the associated problem.
 * @param O The output type of the associated problem.
 * @property index      1-based position of this test case within the problem's example list.
 * @property example    The [Example] that was executed.
 * @property elapsedMs  Wall-clock execution time in milliseconds.
 */
sealed class TestResult<I, O> {

    abstract val index: Int
    abstract val example: Example<I, O>
    abstract val elapsedMs: Double

    /** The display label used in console and markdown output: `"TestCase 1"`. */
    val label: String get() = "TestCase $index"

    /**
     * A test case whose actual output matched the expected output.
     *
     * @property actual The value returned by `Problem.solve(input)`.
     */
    data class Passed<I, O>(
        override val index: Int,
        override val example: Example<I, O>,
        override val elapsedMs: Double,
        val actual: O
    ) : TestResult<I, O>()

    /**
     * A test case whose actual output did NOT match the expected output.
     *
     * Both [actual] and [expected] are retained for diff display in docs.
     *
     * @property actual   The value returned by `Problem.solve(input)`.
     * @property expected The value declared in [Example.testCase].
     */
    data class Failed<I, O>(
        override val index: Int,
        override val example: Example<I, O>,
        override val elapsedMs: Double,
        val actual: O,
        val expected: O
    ) : TestResult<I, O>()

    /**
     * A test case where `Problem.solve(input)` threw an uncaught exception.
     *
     * The exception is captured and surfaced in the report rather than
     * propagating up and aborting the remaining test cases.
     *
     * @property cause The captured [Throwable].
     */
    data class Errored<I, O>(
        override val index: Int,
        override val example: Example<I, O>,
        override val elapsedMs: Double,
        val cause: Throwable
    ) : TestResult<I, O>()

    companion object {
        /**
         * Factory that constructs the correct [TestResult] subtype from a [Result]-wrapped
         * actual value.
         *
         * This helper centralises the comparison logic so [Problem.runTests] stays clean.
         *
         * @param index     1-based test case index.
         * @param example   The executed [Example].
         * @param actual    The `Result<O>` from `runCatching { solve(input) }`.
         * @param elapsedMs Measured execution time.
         * @return [Passed], [Failed], or [Errored] depending on [actual] state and equality.
         */
        fun <I, O> from(
            index: Int,
            example: Example<I, O>,
            actual: Result<O>,
            elapsedMs: Double
        ): TestResult<I, O> {
            return when {
                actual.isFailure -> Errored(
                    index = index,
                    example = example,
                    elapsedMs = elapsedMs,
                    cause = actual.exceptionOrNull()!!
                )
                else -> {
                    val actualValue = actual.getOrThrow()
                    val expectedValue = example.testCase.expected
                    val equal = areEqual(actualValue, expectedValue)
                    if (equal) {
                        Passed(index, example, elapsedMs, actualValue)
                    } else {
                        Failed(index, example, elapsedMs, actualValue, expectedValue)
                    }
                }
            }
        }

        /**
         * Deep equality check that handles JVM array types correctly.
         *
         * Standard `==` on `IntArray`, `Array<T>` etc. checks reference equality.
         * This function unwraps common array types to their content-comparison equivalents
         * before falling back to structural equality.
         *
         * Supported special cases: [IntArray], [LongArray], [DoubleArray], [Array].
         *
         * @param a First value.
         * @param b Second value.
         * @return `true` if the two values are considered equal for test purposes.
         */
        private fun <O> areEqual(a: O, b: O): Boolean = when {
            a is IntArray && b is IntArray -> a.contentEquals(b)
            a is LongArray && b is LongArray -> a.contentEquals(b)
            a is DoubleArray && b is DoubleArray -> a.contentEquals(b)
            a is Array<*> && b is Array<*> -> a.contentDeepEquals(b)
            else -> a == b
        }
    }
}
