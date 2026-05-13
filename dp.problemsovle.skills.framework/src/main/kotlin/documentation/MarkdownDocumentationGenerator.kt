package documentation

import dp.problemsovle.skills.framework.model.ProblemMetadata
import dp.problemsovle.skills.framework.model.ProblemRunReport
import dp.problemsovle.skills.framework.result.TestResult
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Generates structured Markdown documentation for a problem that has passed all its tests.
 *
 * Documentation files are written to `src/main/resources/problems/<slug>.md`.
 * The generator is intentionally stateless — every call to [generate] creates or
 * overwrites exactly one file, with no shared mutable state.
 *
 * ## Generated File Anatomy
 * ```
 * # 1. Two Sum
 *
 * ## Problem Statement
 * ...description...
 *
 * ## Examples
 * ...table of examples...
 *
 * ## Constraints
 * ...bullet list...
 *
 * ## Test Results
 * ...table of pass/fail per case...
 *
 * ## Notes
 * Generated on: YYYY-MM-DD
 * ```
 *
 * ## Extension Point
 * Override [outputDirectory] to redirect output during testing without touching the
 * real `resources` folder.
 *
 * @property outputDirectory Base directory under which `problems/` will be created.
 */
class MarkdownDocumentationGenerator(
    val outputDirectory: String = "src/main/resources"
) {

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    /**
     * Generates a Markdown documentation file for the given [report].
     *
     * The filename is derived from [ProblemMetadata.slug]: `<slug>.md`.
     * If the target directory does not exist it is created automatically.
     * Existing files are overwritten so that the docs always reflect the latest run.
     *
     * @param report The [ProblemRunReport] from a successful test run.
     * @return The [File] that was written, or `null` if an I/O error occurred.
     */
    fun <I, O> generate(report : ProblemRunReport<I, O>): File? {
        return runCatching {
            val subDirs = report.metadata.run {
                platform.displayName + "/" + difficulty.label + "/" + tags.firstOrNull()?.displayName
            }.lowercase()
            val dir = File(outputDirectory, "problems/$subDirs").also { it.mkdirs() }
            val file = File(dir, "${report.metadata.slug}.md")
            file.writeText(buildMarkdown(report))
            println("📄 Documentation generated: ${file.path}")
            file
        }.onFailure { e ->
            System.err.println("⚠ Failed to generate documentation for '${report.metadata.title}': ${e.message}")
        }.getOrNull()
    }

    /**
     * Builds the full Markdown content string for a given [report].
     *
     * Separated from [generate] to allow unit testing without touching the file system.
     *
     * @param report The problem run report to serialise.
     * @return A complete Markdown document as a [String].
     */
    internal fun <I, O> buildMarkdown(report: ProblemRunReport<I, O>): String = buildString {
        val meta = report.metadata
        val statement = report.statement

        // ── Header ────────────────────────────────────────────────────────────
        appendLine("# ${meta.displayName}")
        appendLine()
        appendBadges(meta)
        appendLine()

        // ── Problem Statement ──────────────────────────────────────────────────
        appendLine("## Problem Statement")
        appendLine()
        appendLine(statement.description.trim())
        appendLine()

        // ── Input / Output format ──────────────────────────────────────────────
        if (statement.inputFormat.isNotBlank() || statement.outputFormat.isNotBlank()) {
            appendLine("### Input / Output")
            if (statement.inputFormat.isNotBlank()) {
                appendLine("- **Input:** ${statement.inputFormat}")
            }
            if (statement.outputFormat.isNotBlank()) {
                appendLine("- **Output:** ${statement.outputFormat}")
            }
            appendLine()
        }

        // ── Examples ──────────────────────────────────────────────────────────
        appendLine("## Examples")
        appendLine()
        statement.examples.forEachIndexed { i, example ->
            appendLine("**Example ${i + 1}:**")
            appendLine()
            appendLine("```")
            appendLine("Input:  ${example.testCase.input.displayString()}")
            appendLine("Output: ${example.testCase.expected.displayString()}")
            appendLine("```")
            appendLine()
            appendLine("*${example.explanation.trim()}*")
            appendLine()
        }

        // ── Constraints ────────────────────────────────────────────────────────
        if (statement.constraints.isNotEmpty()) {
            appendLine("## Constraints")
            appendLine()
            statement.constraints.forEach { appendLine("- `$it`") }
            appendLine()
        }



        // ── Solution ────────────────────────────────────────────────────────
        if (report.sourceCode.isNotEmpty()) {
            appendLine("## Solution")
            appendLine()
            appendLine(report.sourceCode)
            appendLine()
        }


        // ── Test Results ───────────────────────────────────────────────────────
        appendLine("## Test Execution Results")
        appendLine()
        appendLine("| # | Input | Expected | Actual | Status | Time (ms) |")
        appendLine("|---|-------|----------|--------|--------|-----------|")
        report.results.forEach { result ->
            appendTestResultRow(result)
        }
        appendLine()

        val icon = if (report.allPassed) "✅" else "❌"
        appendLine("**${report.passed}/${report.total} tests passed** $icon")
        appendLine()

        // ── Footer ─────────────────────────────────────────────────────────────
        appendLine("---")
        appendLine()
        appendLine("*Generated on ${LocalDate.now().format(dateFormatter)}*")
        meta.url?.let { appendLine("*Source: [$it]($it)*") }
    }

    /** Appends GitHub-Flavoured Markdown badge pills for difficulty and tags. */
    private fun StringBuilder.appendBadges(meta: ProblemMetadata) {
        val diffColor = when (meta.difficulty.label) {
            "Easy" -> "brightgreen"
            "Medium" -> "yellow"
            "Hard" -> "red"
            else -> "lightgrey"
        }
        append("![Difficulty](https://img.shields.io/badge/Difficulty-${meta.difficulty.label}-$diffColor) ")
        append("![Platform](https://img.shields.io/badge/Platform-${meta.platform.displayName}-blue) ")
        meta.tags.forEach { tag ->
            append("![${tag.displayName}](https://img.shields.io/badge/-${tag.displayName.replace(" ", "%20")}-blueviolet) ")
        }
        appendLine()
    }

    /** Appends a single table row for a [TestResult]. */
    private fun <I, O> StringBuilder.appendTestResultRow(result: TestResult<I, O>) {
        val input = result.example.testCase.input.displayString()
        val expected = result.example.testCase.expected.displayString()
        val time = "%.3f".format(result.elapsedMs)

        when (result) {
            is TestResult.Passed -> appendLine(
                "| ${result.index} | `$input` | `$expected` | `${result.actual.displayString()}` | ✅ PASS | $time |"
            )
            is TestResult.Failed -> appendLine(
                "| ${result.index} | `$input` | `$expected` | `${result.actual.displayString()}` | ❌ FAIL | $time |"
            )
            is TestResult.Errored -> appendLine(
                "| ${result.index} | `$input` | `$expected` | `${result.cause.javaClass.simpleName}` | 💥 ERROR | $time |"
            )
        }
    }

    /** Handles JVM array types for readable display. */
    private fun Any?.displayString(): String = when (this) {
        is IntArray -> contentToString()
        is LongArray -> contentToString()
        is DoubleArray -> contentToString()
        is Array<*> -> contentDeepToString()
        else -> toString()
    }
}
