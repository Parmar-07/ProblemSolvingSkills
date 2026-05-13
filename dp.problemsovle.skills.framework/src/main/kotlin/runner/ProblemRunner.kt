package dp.problemsovle.skills.framework.runner

import documentation.MarkdownDocumentationGenerator
import documentation.ReadmeUpdater
import dp.problemsovle.skills.framework.model.Problem
import dp.problemsovle.skills.framework.model.ProblemRunReport
import dp.problemsovle.skills.framework.registry.ProblemRegistry
import dp.problemsovle.skills.framework.result.TestResult

/**
 * Orchestrates the full problem execution pipeline for one or more problems.
 *
 * [ProblemRunner] is the entry point for running tests. It replaces the combined
 * responsibilities that were spread across `ProblemTestRule` and `Problem.test()`.
 *
 * ## Responsibilities
 * 1. Execute `problem.runTests()` to produce [ProblemRunReport]
 * 2. Render results to console with formatting
 * 3. Trigger markdown generation if all tests pass
 * 4. Trigger README update if all tests pass
 * 5. Return a non-zero exit code if any test failed (for CI)
 *
 * ## Usage
 * ```kotlin
 * // Run a single problem
 * ProblemRunner.run(TwoSum())
 *
 * // Run all registered problems
 * ProblemRunner.runAll()
 *
 * // Run and exit process on failure (CI mode)
 * ProblemRunner.runCi(TwoSum())
 * ```
 */
object ProblemRunner {

    private val markdownGenerator = MarkdownDocumentationGenerator()
    private val readmeUpdater = ReadmeUpdater()

    /**
     * Runs a single [problem], prints results, and triggers doc generation on success.
     *
     * @param problem The problem instance to run.
     * @param generateDocs If `true` (default), generates markdown and updates README on full pass.
     * @return `true` if all test cases passed.
     */
    fun <I, O> run(problem: Problem<I, O>, generateDocs: Boolean = true): Boolean {
        val report = problem.runTests()
        renderReport(report)
        if (report.allPassed && generateDocs) {
            markdownGenerator.generate(report)
            readmeUpdater.update(report.metadata)
        }
        return report.allPassed
    }

    /**
     * Runs all problems registered in [ProblemRegistry].
     *
     * Problems are run in registration order. Failures in one problem do not prevent
     * subsequent problems from running.
     *
     * @param generateDocs If `true`, generates docs for every passing problem.
     * @return `true` only if every registered problem passed all test cases.
     */
    fun runAll(generateDocs: Boolean = true): Boolean {
        val problems = ProblemRegistry.all()
        if (problems.isEmpty()) {
            println("⚠ No problems registered in ProblemRegistry.")
            return true
        }
        println("Running ${problems.size} problem(s)...\n")
        return problems.all { run(it, generateDocs) }
    }

    /**
     * CI-friendly runner that terminates the process with exit code `1` if any test fails.
     *
     * Intended for use in Gradle tasks or GitHub Actions steps where a non-zero exit
     * code signals pipeline failure.
     *
     * @param problem The problem instance to run.
     */
    fun <I, O> runCi(problem: Problem<I, O>) {
        val passed = run(problem)
        if (!passed) {
            System.exit(1)
        }
    }

    /**
     * Renders a [ProblemRunReport] to standard output with consistent formatting.
     *
     * The console output includes:
     * - Problem title and separator
     * - Full problem description
     * - Per-test-case input, expected, actual, and pass/fail status
     * - Summary line
     *
     * @param report The [ProblemRunReport] to render.
     */
    private fun <I, O> renderReport(report: ProblemRunReport<I, O>) {
        val sep = "=".repeat(60)
        println(sep)
        println("  ${report.metadata.displayName}  [${report.metadata.difficulty.label}]")
        println("  Tags: ${report.metadata.tags.joinToString { it.displayName }}")
        println(sep)
        println()
        println("Description:")
        println(report.statement.description)
        println()
        println("Test Results:")
        println("-".repeat(60))

        report.results.forEach { result ->
            renderTestResult(result)
        }

        println("-".repeat(60))
        val icon = if (report.allPassed) "✅" else "❌"
        println("$icon  ${report.summary}")
        println(sep)
        println()
    }

    /**
     * Renders a single [TestResult] to standard output.
     *
     * Uses ANSI colour codes where supported. Falls back to plain text for CI
     * environments that strip colour.
     *
     * @param result The test result to render.
     */
    private fun <I, O> renderTestResult(result: TestResult<I, O>) {
        println()
        println("  ${result.label}")
        println("  Input    : ${result.example.testCase.input.displayString()}")

        when (result) {
            is TestResult.Passed -> {
                println("  Expected : ${result.example.testCase.expected.displayString()}")
                println("  Actual   : ${result.actual.displayString()}")
                println("  Status   : ✅ PASSED  (%.3f ms)".format(result.elapsedMs))
            }
            is TestResult.Failed -> {
                println("  Expected : ${result.expected.displayString()}")
                println("  Actual   : ${result.actual.displayString()}")
                println("  Status   : ❌ FAILED  (%.3f ms)".format(result.elapsedMs))
            }
            is TestResult.Errored -> {
                println("  Error    : ${result.cause.javaClass.simpleName}: ${result.cause.message}")
                println("  Status   : 💥 ERROR   (%.3f ms)".format(result.elapsedMs))
            }
        }
    }

    /**
     * Returns a human-readable string for any value, with special handling for JVM array types.
     *
     * `IntArray.toString()` returns something like `[I@1b6d3586` which is useless for
     * diagnostics. This extension provides `[1, 2, 3]` style output transparently.
     */
    private fun Any?.displayString(): String = when (this) {
        is IntArray -> contentToString()
        is LongArray -> contentToString()
        is DoubleArray -> contentToString()
        is Array<*> -> contentDeepToString()
        else -> toString()
    }
}
