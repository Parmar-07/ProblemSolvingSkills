package dp.problemsovle.skills.framework.model

import dp.problemsovle.skills.framework.result.TestResult

/**
 * Core abstraction representing a single competitive programming problem.
 *
 * This sealed-style abstract class forms the foundation of the problem-solving framework.
 * Every concrete problem must extend this class, define its [ProblemStatement], and
 * implement the [solve] function that contains the actual algorithm.
 *
 * ## Design Rationale
 * By parameterising both input type [I] and output type [O], the framework achieves
 * complete type-safety across problem definitions, test execution, and documentation
 * generation without requiring unsafe casts in domain logic.
 *
 * ## Implementing a Problem
 * ```kotlin
 * class TwoSum : Problem<TwoSumInput, IntArray>() {
 *
 *     override val metadata = ProblemMetadata(
 *         id = 1,
 *         title = "Two Sum",
 *         difficulty = Difficulty.EASY,
 *         tags = setOf(Tag.ARRAY, Tag.HASH_MAP)
 *     )
 *
 *     override val statement: ProblemStatement<TwoSumInput, IntArray> =
 *         ProblemStatement.build { ... }
 *
 *     override fun solve(input: TwoSumInput): IntArray { ... }
 * }
 * ```
 *
 * @param I The fully-typed input parameter. Use a dedicated data class for multi-parameter problems.
 * @param O The output type returned by the algorithm.
 */
abstract class Problem<I, O> {

    /**
     * Structured metadata about this problem used in README generation, tagging, and indexing.
     */
    abstract val metadata: ProblemMetadata

    /**
     * The complete problem statement including description, examples, and constraints.
     * Built via [dinesh.parmar.framework.core.builder.ProblemStatementBuilder].
     */
    abstract val statement: ProblemStatement<I, O>

    /**
     * Core algorithm implementation.
     *
     * @param input The typed input for this problem instance.
     * @return The computed output, matching the expected type [O].
     */
    abstract fun solve(input: I): O

    /**
     * Executes all test cases defined in [statement] and collects structured [TestResult]s.
     *
     * This function is intentionally side-effect-free regarding I/O; it returns a
     * [ProblemRunReport] that callers (such as the runner or documentation engine) can
     * act upon. Console rendering is delegated to the caller.
     *
     * @return A [ProblemRunReport] capturing pass/fail state, timing, and per-case results.
     */
    fun runTests(): ProblemRunReport<I, O> {
        val results = statement.examples.mapIndexed { index, example ->
            val start = System.nanoTime()
            val actual = runCatching { solve(example.testCase.input) }
            val elapsedMs = (System.nanoTime() - start) / 1_000_000.0

            TestResult.from(
                index = index + 1,
                example = example,
                actual = actual,
                elapsedMs = elapsedMs
            )
        }

        return ProblemRunReport(
            metadata = metadata,
            statement = statement,
            sourceCode = readSourceCode() ,
            results = results
        )
    }

    /**
     * Raw Kotlin source code snippet used for markdown/document generation.
     *
     * Child classes should override this property and return only the
     * algorithm implementation or the desired code section.
     */
    open val sourceCode: String = ""

    /**
     * Converts [sourceCode] into a markdown-formatted Kotlin code block.
     *
     * Example output:
     * ```markdown
     * ```kotlin
     * // code
     * ```
     * ```
     *
     * @return Formatted markdown code block.
     */
    private fun readSourceCode(): String {
        if (sourceCode.isBlank()) return ""

        return buildString {
            appendLine("```kotlin")
            appendLine(sourceCode.trimIndent())
            appendLine("```")
        }
    }


}
