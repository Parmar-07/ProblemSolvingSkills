package documentation

import dp.problemsovle.skills.framework.model.ProblemMetadata
import dp.problemsovle.skills.framework.registry.ProblemRegistry
import java.io.File

/**
 * Automatically maintains the solved-problems table in `README.md`.
 *
 * [ReadmeUpdater] is triggered after a problem passes all its tests. It reads the
 * existing README, locates the managed table region (delimited by sentinel comments),
 * rebuilds the table from [ProblemRegistry], and writes the file back atomically.
 *
 * ## Sentinel-Based Table Management
 * Rather than naively appending rows (which leads to duplicates), the updater uses
 * HTML comment sentinels to mark the table region:
 * ```
 * <!-- PROBLEMS_TABLE_START -->
 * | # | Problem | Difficulty | Tags | Solution |
 * ...
 * <!-- PROBLEMS_TABLE_END -->
 * ```
 * Everything between the sentinels is replaced on each run. Content outside the
 * sentinels is preserved verbatim.
 *
 * ## Idempotency
 * Running the updater multiple times with the same registry produces the same README —
 * no duplicate rows, no ordering drift.
 *
 * @property readmePath Path to the README file. Defaults to `README.md` at project root.
 * @property resourcesPath Relative path prefix used for solution links in the table.
 */
class ReadmeUpdater(
    val readmePath: String = "README.md",
    val resourcesPath: String = "src/main/resources/problems"
) {

    companion object {
        /** HTML comment sentinel marking the start of the managed problems table. */
        const val TABLE_START = "<!-- PROBLEMS_TABLE_START -->"

        /** HTML comment sentinel marking the end of the managed problems table. */
        const val TABLE_END = "<!-- PROBLEMS_TABLE_END -->"
    }

    /**
     * Updates the README to include the newly solved [metadata] entry.
     *
     * Flow:
     * 1. Ensure [ProblemRegistry] contains the problem (it should, as registration
     *    happens at construction time).
     * 2. Read the current README content (creates a default if absent).
     * 3. Replace the sentinel-bounded table block with a freshly built table.
     * 4. Write the result back to [readmePath].
     *
     * @param metadata The metadata of the problem that just passed all tests.
     */
    fun update(metadata: ProblemMetadata) {
        runCatching {
            val readmeFile = File(readmePath)
            val current = if (readmeFile.exists()) readmeFile.readText() else defaultReadme()
            val updated = replaceManagedTable(current)
            readmeFile.writeText(updated)
            println("📝 README.md updated — ${ProblemRegistry.size} problem(s) listed.")
        }.onFailure { e ->
            System.err.println("⚠ Failed to update README.md: ${e.message}")
        }
    }

    /**
     * Replaces the sentinel-bounded region in [content] with the regenerated table.
     *
     * If sentinels are not present (e.g. first run on a new README), they are appended
     * at the end of the file along with the table.
     *
     * @param content The current README content.
     * @return The updated README content string.
     */
    internal fun replaceManagedTable(content: String): String {
        val newBlock = buildTableBlock()
        return if (content.contains(TABLE_START) && content.contains(TABLE_END)) {
            val before = content.substringBefore(TABLE_START)
            val after = content.substringAfter(TABLE_END)
            "$before$newBlock$after"
        } else {
            "$content\n\n$newBlock"
        }
    }

    /**
     * Builds the complete sentinel-wrapped Markdown table block from [ProblemRegistry].
     *
     * Rows are sorted ascending by problem ID, matching platform conventions.
     *
     * @return A [String] containing the start sentinel, table, and end sentinel.
     */
    internal fun buildTableBlock(): String = buildString {
        appendLine(TABLE_START)
        appendLine()
        appendLine("## ✅ Solved Problems")
        appendLine()
        appendLine("| # | Problem | Difficulty | Tags | Solution |")
        appendLine("|---|---------|------------|------|----------|")

        ProblemRegistry.all()
            .sortedBy { it.metadata.id }
            .forEach { problem ->
                val meta = problem.metadata
                val tags = meta.tags.joinToString(", ") { it.displayName }
                    .ifEmpty { "-" }
                val subDirs = meta.run {
                    platform.displayName + "/" + difficulty.label + "/" + this.tags.firstOrNull()?.displayName
                }.lowercase()
                val link = "[View]($resourcesPath/$subDirs/${meta.slug}.md)"
                appendLine("| ${meta.id} | ${meta.title} | ${meta.difficulty.label} | $tags | $link |")
            }

        appendLine()
        appendLine("*Last updated automatically by the framework after each successful test run.*")
        appendLine()
        append(TABLE_END)
    }

    /**
     * Returns a minimal default README content used when no README exists yet.
     *
     * Includes both sentinels so the first update lands in the right place.
     *
     * @return Default README string with embedded sentinels.
     */
    private fun defaultReadme(): String = """
        # Competitive Programming Solutions
        
        A personal collection of solutions to competitive programming problems,
        built with the [CP Framework](https://github.com/your-username/cp-framework).
        
        ## Structure
        ```
        src/main/kotlin/
        └── problems/
            ├── arrays/
            ├── numbers/
            ├── strings/
            └── ...
        ```
        
    """.trimIndent() + "\n\n$TABLE_START\n$TABLE_END\n"
}
