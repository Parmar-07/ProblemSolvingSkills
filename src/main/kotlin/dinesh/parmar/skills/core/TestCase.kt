package dinesh.parmar.skills.core


/**
 * A generic data container used to define data-driven test scenarios.
 *
 * This class pairs a specific input value with its corresponding expected output value,
 * allowing tests to iterate over a collection of cases to verify logic consistency.
 *
 * @param I The type of the input data provided to the function under test.
 * @param O The type of the expected output or result.
 *
 * @property input The sample data or parameters used as the test trigger.
 * @property output The expected result or state change that should occur after processing the input.
 *
 * @example
 * ```kotlin
 * val cases = listOf(
 *     TestCase(input = "abc", output = 3),
 *     TestCase(input = "", output = 0)
 * )
 * ```
 */
data class TestCase<I,O>(
    val input : I,
    val output : O
)
