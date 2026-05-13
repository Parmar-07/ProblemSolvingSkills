package dinesh.parmar.skills.problems.numbers

import builder.ProblemStatementBuilder
import dp.problemsovle.skills.framework.model.*


/**
 * Solution for LeetCode #9 — *Palindrome Number*.
 *
 * ## Problem Summary
 * Given an integer `x`, return `true` if `x` is a palindrome (reads the same
 * forwards and backwards), and `false` otherwise.
 *
 * ## Algorithm — Half-Reversal (No String Conversion)
 * Instead of converting to a string, we reverse only the **second half** of
 * the number mathematically and compare it to the first half.
 *
 * ### Early-Exit Conditions (O(1))
 * 1. **Negative numbers** are never palindromes (e.g. `-121` → `121-` reversed).
 * 2. **Multiples of 10** (excluding 0) are never palindromes (leading zero problem).
 *
 * ### Half-Reversal Logic
 * We extract digits from the right of `original` and build `reversed` until
 * `reversed >= original`. At that point we've processed exactly half the digits.
 *
 * | Iteration | `original` | `reversed` | digit |
 * |-----------|-----------|-----------|-------|
 * | Start     | 12321     | 0         |       |
 * | 1         | 1232      | 1         | 1     |
 * | 2         | 123       | 12        | 2     |
 * | 3         | 12        | 123       | 3     |
 * | Loop ends (reversed > original)           |
 *
 * **Odd-length check:** For `12321`, `original = 12`, `reversed = 123`.
 * The middle digit (`3`) is irrelevant, so we compare `original == reversed / 10`.
 *
 * **Even-length check:** For `1221`, `original = 12`, `reversed = 12`.
 * Direct equality `original == reversed` suffices.
 *
 * ### Complexity
 * - **Time:** O(log₁₀ n) — we process half the digits
 * - **Space:** O(1) — no auxiliary structures
 *
 * @see <a href="https://leetcode.com/problems/palindrome-number/">LeetCode #9</a>
 */
class PalindromeNumber : Problem<Int, Boolean>() {

    /**
     * Problem metadata used for registry look-up, documentation, and README table.
     */
    override val metadata: ProblemMetadata = ProblemMetadata(
        id = 9,
        title = "Palindrome Number",
        difficulty = Difficulty.EASY,
        tags = setOf(Tag.MATH, Tag.NUMBER_THEORY),
        url = "https://leetcode.com/problems/palindrome-number/"
    )

    /**
     * Problem statement with description, worked examples, and constraints.
     */
    override val statement: ProblemStatement<Int, Boolean> =
        ProblemStatementBuilder<Int, Boolean>()
            .description(
                "Given an integer x, return true if x is a palindrome, and false otherwise."
            )
            .example(
                explanation = "121 reads as 121 from left to right and from right to left.",
                input = 121,
                expected = true
            )
            .example(
                explanation = "From left to right it reads -121. From right to left it becomes 121-. " +
                        "Therefore it is not a palindrome.",
                input = -121,
                expected = false
            )
            .example(
                explanation = "Reads 01 from right to left. Therefore it is not a palindrome.",
                input = 10,
                expected = false
            )
            .example(
                explanation = "0 is its own reverse, so it is a palindrome.",
                input = 0,
                expected = true
            )
            .constraint("-2^31 <= x <= 2^31 - 1")
            .inputFormat("A single integer x.")
            .outputFormat("true if x is a palindrome, false otherwise.")
            .build()

    /**
     * Determines whether the integer [input] is a palindrome using half-reversal.
     *
     * No string conversion is used. The algorithm reverses only the lower half of
     * the number and compares it to the upper half.
     *
     * @param input The integer to test.
     * @return `true` if [input] is a palindrome, `false` otherwise.
     */
    override fun solve(input: Int): Boolean {
        // Negative numbers and non-zero multiples of 10 can never be palindromes
        if (input < 0 || (input != 0 && input % 10 == 0)) return false

        var original = input
        var reversed = 0

        // Build the reversed half until it meets or exceeds the remaining original
        while (original > reversed) {
            reversed = reversed * 10 + (original % 10)
            original /= 10
        }

        // Even length: 1221 → original=12, reversed=12
        // Odd length:  12321 → original=12, reversed=123, drop middle digit with /10
        return original == reversed || original == reversed / 10
    }

    /**
     * Kotlin implementation snippet used for README/MD generation.
     */
    override val sourceCode: String
        get() = """
        override fun solve(input: Int): Boolean {

            // Negative numbers and non-zero multiples of 10
            // can never be palindromes
            if (input < 0 || (input != 0 && input % 10 == 0)) {
                return false
            }

            var original = input
            var reversed = 0

            // Reverse only half of the number
            while (original > reversed) {
                reversed = reversed * 10 + (original % 10)
                original /= 10
            }

            // Even length:
            // 1221 -> original=12, reversed=12
            //
            // Odd length:
            // 12321 -> original=12, reversed=123
            // Remove middle digit using /10
            return original == reversed ||
                    original == reversed / 10
        }
    """.trimIndent()


}
