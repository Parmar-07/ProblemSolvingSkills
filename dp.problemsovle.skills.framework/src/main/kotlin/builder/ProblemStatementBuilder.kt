package builder

import dp.problemsovle.skills.framework.model.Example
import dp.problemsovle.skills.framework.model.ProblemStatement
import dp.problemsovle.skills.framework.model.TestCase

/**
 * Type-safe fluent builder for constructing [ProblemStatement] instances.
 *
 * Provides a clean DSL for defining the description, examples, and constraints of a
 * problem directly inside the [dinesh.parmar.framework.core.model.Problem] subclass.
 * The builder enforces preconditions at build-time (not runtime), catching missing
 * fields during development rather than during test execution.
 *
 * ## Usage
 * ```kotlin
 * override val statement = ProblemStatementBuilder<Int, Boolean>()
 *     .description("Given an integer x, return true if x is a palindrome.")
 *     .example(
 *         explanation = "121 reads the same from left to right and right to left.",
 *         input = 121,
 *         expected = true
 *     )
 *     .constraint("-2^31 <= x <= 2^31 - 1")
 *     .inputFormat("A single integer x.")
 *     .outputFormat("true if x is a palindrome, false otherwise.")
 *     .build()
 * ```
 *
 * ## Type Safety
 * Unlike the original [ProblemStatementBuilder] which used `mutableListOf<Example<*, *>>()`
 * and suppressed unchecked casts, this builder is fully typed at compile time.
 *
 * @param I The problem's input type, inferred from the first [example] call.
 * @param O The problem's output type, inferred from the first [example] call.
 */
class ProblemStatementBuilder<I, O> {

    private var description: String = ""
    private val examples: MutableList<Example<I, O>> = mutableListOf()
    private val constraints: MutableList<String> = mutableListOf()
    private var inputFormat: String = ""
    private var outputFormat: String = ""

    /**
     * Sets the problem description text.
     *
     * Multi-line descriptions are supported via triple-quoted Kotlin strings.
     *
     * @param text The problem description. Must not be blank.
     * @return This builder instance for chaining.
     */
    fun description(text: String) = apply {
        description = text
    }

    /**
     * Adds a worked example that functions as both documentation and a runnable test case.
     *
     * @param explanation Human-readable explanation shown in the problem statement and docs.
     * @param input       The typed input for this example.
     * @param expected    The expected output for this example.
     * @return This builder instance for chaining.
     */
    fun example(explanation: String, input: I, expected: O) = apply {
        examples.add(Example(explanation, TestCase(input, expected)))
    }

    /**
     * Adds a constraint string to the problem statement.
     *
     * Constraints should follow the canonical format used on problem platforms:
     * `"1 <= nums.length <= 3000"`, `"-10^9 <= nums[i] <= 10^9"`, etc.
     *
     * @param constraint The constraint string to append.
     * @return This builder instance for chaining.
     */
    fun constraint(constraint: String) = apply {
        constraints.add(constraint)
    }

    /**
     * Convenience overload accepting vararg constraints for concise definition.
     *
     * ```kotlin
     * .constraints(
     *     "1 <= nums.length <= 3000",
     *     "-10^5 <= nums[i] <= 10^5"
     * )
     * ```
     *
     * @param values One or more constraint strings.
     * @return This builder instance for chaining.
     */
    fun constraints(vararg values: String) = apply {
        constraints.addAll(values)
    }

    /**
     * Sets an optional description of the expected input format.
     *
     * Included in generated markdown under the **Input** section.
     *
     * @param format Human-readable input format description.
     * @return This builder instance for chaining.
     */
    fun inputFormat(format: String) = apply {
        inputFormat = format
    }

    /**
     * Sets an optional description of the expected output format.
     *
     * Included in generated markdown under the **Output** section.
     *
     * @param format Human-readable output format description.
     * @return This builder instance for chaining.
     */
    fun outputFormat(format: String) = apply {
        outputFormat = format
    }

    /**
     * Constructs and returns an immutable [ProblemStatement].
     *
     * ## Preconditions (enforced at build time)
     * - [description] must not be blank.
     * - At least one [example] must have been added.
     *
     * @throws IllegalStateException if any precondition is violated.
     * @return An immutable [ProblemStatement] ready for use.
     */
    fun build(): ProblemStatement<I, O> {
        check(description.isNotBlank()) {
            "ProblemStatement requires a non-blank description. Call .description(...) before .build()."
        }
        check(examples.isNotEmpty()) {
            "ProblemStatement requires at least one example. Call .example(...) before .build()."
        }
        return ProblemStatement(
            description = description,
            examples = examples.toList(),
            constraints = constraints.toList(),
            inputFormat = inputFormat,
            outputFormat = outputFormat
        )
    }
}
