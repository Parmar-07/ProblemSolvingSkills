package dp.problemsovle.skills.framework.model

import dp.problemsovle.skills.framework.result.TestResult

/**
 * Immutable aggregate report produced by running all test cases of a single [Problem].
 *
 * [ProblemRunReport] is the canonical output of `Problem.runTests()`. It is designed to be
 * passed through a pipeline of consumers:
 * - **Console renderer** — prints pass/fail with colour codes
 * - **Markdown generator** — serialises to `.md` in `resources/problems/`
 * - **README updater** — extracts metadata to update the solved-problems table
 * - **CI reporter** — exits non-zero if [allPassed] is false
 *
 * This separates the *execution concern* (running tests) from the *reporting concern*
 * (what to do with results), following the Single Responsibility Principle.
 *
 * @param I         The problem's input type.
 * @param O         The problem's output type.
 * @property metadata  Metadata identifying the problem (id, title, tags, etc.).
 * @property statement The full problem statement for documentation generation.
 * @property results   Ordered list of per-test-case [TestResult]s.
 */
data class ProblemRunReport<I, O>(
    val metadata: ProblemMetadata,
    val statement: ProblemStatement<I, O>,
    val sourceCode: String,
    val results: List<TestResult<I, O>>
) {
    /** Total number of test cases executed. */
    val total: Int get() = results.size

    /** Count of test cases that passed. */
    val passed: Int get() = results.count { it is TestResult.Passed }

    /** Count of test cases that failed or threw an exception. */
    val failed: Int get() = total - passed

    /** `true` only when every test case in [results] has a [TestResult.Passed] status. */
    val allPassed: Boolean get() = failed == 0

    /** Total wall-clock time across all test executions, in milliseconds. */
    val totalElapsedMs: Double get() = results.sumOf { it.elapsedMs }

    /**
     * One-line summary suitable for console or CI log output.
     *
     * Example: `"[1. Two Sum] 3/3 passed (0.42 ms)"`
     */
    val summary: String
        get() = "[${metadata.displayName}] $passed/$total passed (%.2f ms)".format(totalElapsedMs)
}
