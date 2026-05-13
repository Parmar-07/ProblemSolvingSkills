package dp.problemsovle.skills.framework.model

/**
 * Immutable value object encapsulating all human-readable elements of a problem's specification.
 *
 * [ProblemStatement] is the single source of truth for a problem's description, worked examples,
 * and constraints. It is consumed by both the console renderer and the Markdown documentation
 * generator, ensuring the two output surfaces stay in sync automatically.
 *
 * ## Immutability Contract
 * All fields are `val` and collections are typed as [List] (read-only view). Callers
 * cannot mutate the statement after construction, preventing accidental shared-state bugs
 * when the same statement is referenced across tests and docs.
 *
 * @param I The problem's input type.
 * @param O The problem's output type.
 * @property description Full problem description text.
 * @property examples    Ordered list of [Example]s as they appear on the problem page.
 * @property constraints Ordered list of constraint strings, e.g. `"1 <= nums.length <= 3000"`.
 * @property inputFormat Optional description of the input format for documentation purposes.
 * @property outputFormat Optional description of the output format.
 *
 * @see dinesh.parmar.framework.core.builder.ProblemStatementBuilder for the DSL constructor.
 */
data class ProblemStatement<I, O>(
    val description: String,
    val examples: List<Example<I, O>>,
    val constraints: List<String>,
    val inputFormat: String = "",
    val outputFormat: String = ""
)

/**
 * A single worked example pairing a test case with a human-readable explanation.
 *
 * An [Example] is distinct from a plain [TestCase] in that it carries an [explanation]
 * intended for documentation. All examples double as runnable test cases.
 *
 * @param I The input type.
 * @param O The output type.
 * @property explanation  Narrative explanation shown in docs and console output.
 * @property testCase     The executable [TestCase] with typed input/output.
 */
data class Example<I, O>(
    val explanation: String,
    val testCase: TestCase<I, O>
)

/**
 * Minimal, type-safe data container binding a single input to its expected output.
 *
 * [TestCase] is the execution primitive of the framework. The runner invokes
 * `problem.solve(testCase.input)` and compares the result against `testCase.expected`
 * using the configured [OutputMatcher].
 *
 * ## Structural Equality
 * As a `data class`, two [TestCase] instances are equal iff both [input] and [expected]
 * are structurally equal. Note: for array types (`IntArray`, `Array<T>`) Kotlin's default
 * equality is referential — use a custom [OutputMatcher] or wrap in `List` when needed.
 *
 * @param I The input type, e.g. `Int`, `IntArray`, or a custom data class.
 * @param O The expected output type.
 * @property input    The value passed to the algorithm under test.
 * @property expected The value the algorithm must produce to pass.
 *
 * @sample dinesh.parmar.framework.core.model.TestCaseSamples.primitives
 */
data class TestCase<I, O>(
    val input: I,
    val expected: O
)

/** Sample usages referenced by KDoc `@sample` tags. */
private object TestCaseSamples {
    fun primitives() {
        val tc = TestCase(input = 121, expected = true)
        println(tc.input)    // 121
        println(tc.expected) // true
    }
}
