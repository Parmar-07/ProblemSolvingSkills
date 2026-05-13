package dinesh.parmar.skills.problems.arrays

import builder.ProblemStatementBuilder
import dp.problemsovle.skills.framework.model.*

/**
 * Solution for LeetCode #2553 — *Separate the Digits in an Array*.
 *
 * ## Problem Summary
 * Given an array of positive integers, return a new array containing each digit
 * of every number in the original order.
 *
 * For example: `[13, 25]` → `[1, 3, 2, 5]`
 *
 * ## Algorithm — Digit Extraction via Modulo
 * For each number in the input array:
 * 1. Repeatedly extract the last digit using `n % 10`.
 * 2. Accumulate digits in reverse order into a temporary list.
 * 3. Reverse the temporary list and append to the result.
 *
 * This avoids string conversion, keeping the approach purely arithmetic.
 *
 * ### Complexity
 * - **Time:** O(n × d) where `n` = number of elements, `d` = average digit count
 * - **Space:** O(n × d) for the result array
 *
 * ## Example Trace for input `[13, 25]`
 * ```
 * num = 13
 *   iteration 1: digit = 13 % 10 = 3, n = 1,  digits = [3]
 *   iteration 2: digit =  1 % 10 = 1, n = 0,  digits = [3, 1]
 *   reversed → [1, 3] → result = [1, 3]
 *
 * num = 25
 *   iteration 1: digit = 25 % 10 = 5, n = 2,  digits = [5]
 *   iteration 2: digit =  2 % 10 = 2, n = 0,  digits = [5, 2]
 *   reversed → [2, 5] → result = [1, 3, 2, 5]
 * ```
 *
 * @see <a href="https://leetcode.com/problems/separate-the-digits-in-an-array/">LeetCode #2553</a>
 */
class SeparateDigits : Problem<IntArray, IntArray>() {

    /**
     * Problem metadata used for registry look-up, documentation, and README table.
     */
    override val metadata: ProblemMetadata = ProblemMetadata(
        id = 2553,
        title = "Separate the Digits in an Array",
        difficulty = Difficulty.EASY,
        tags = setOf(Tag.ARRAY, Tag.MATH),
        url = "https://leetcode.com/problems/separate-the-digits-in-an-array/"
    )

    /**
     * Problem statement with description, worked examples, and constraints.
     * Built via [ProblemStatementBuilder] to keep the DSL clean and validated.
     */
    override val statement: ProblemStatement<IntArray, IntArray> =
        ProblemStatementBuilder<IntArray, IntArray>()
            .description(
                """
                Given an array of positive integers nums, return an array answer that consists
                of the digits of each integer in nums after separating them in the same order
                they appear in nums.
                
                To separate the digits of an integer is to get all the digits it has in the
                same order. For example, for the integer 10921, the separation of its digits
                is [1, 0, 9, 2, 1].
                """.trimIndent()
            )
            .example(
                explanation = "The separations of 13, 25, 83, 77 are [1,3], [2,5], [8,3], [7,7]. " +
                        "Concatenated in order: [1,3,2,5,8,3,7,7].",
                input = intArrayOf(13, 25, 83, 77),
                expected = intArrayOf(1, 3, 2, 5, 8, 3, 7, 7)
            )
            .example(
                explanation = "Each single-digit number separates to itself: [7,1,3,9].",
                input = intArrayOf(7, 1, 3, 9),
                expected = intArrayOf(7, 1, 3, 9)
            )
            .constraints(
                "1 <= nums.length <= 1000",
                "1 <= nums[i] <= 10^5"
            )
            .inputFormat("An array of positive integers.")
            .outputFormat("An array of individual digits in the original order.")
            .build()

    /**
     * Separates the digits of each number in [input] and returns them as a flat array.
     *
     * The implementation uses modulo arithmetic to extract digits in reverse order,
     * then reverses each number's digit list before appending to the result.
     *
     * @param input An [IntArray] of positive integers.
     * @return An [IntArray] of individual digits in the order they appear in [input].
     */
    override fun solve(input: IntArray): IntArray {
        val result = mutableListOf<Int>()

        for (num in input) {
            var n = num
            val digits = mutableListOf<Int>()

            // Extract digits from least-significant to most-significant
            while (n > 0) {
                digits.add(n % 10)  // e.g. 13 % 10 = 3
                n /= 10             // e.g. 13 / 10 = 1
            }

            // Reverse to restore original digit order, then append
            digits.reversed().forEach { result.add(it) }
        }

        return result.toIntArray()
    }


    /**
     * Kotlin implementation snippet used for README/MD generation.
     */
    override val sourceCode: String
        get() = """
        override fun solve(input: IntArray): IntArray {
          val result = mutableListOf<Int>()

          for (num in input) {
            var n = num
            val digits = mutableListOf<Int>()

            // Extract digits from least-significant to most-significant
            while (n > 0) {
                digits.add(n % 10)  // e.g. 13 % 10 = 3
                n /= 10             // e.g. 13 / 10 = 1
            }

            // Reverse to restore original digit order, then append
            digits.reversed().forEach { result.add(it) }
          }

        return result.toIntArray()
       }
    """.trimIndent()
}
