package dinesh.parmar.skills.problems.numbers

import dinesh.parmar.skills.core.Problem
import dinesh.parmar.skills.core.ProblemStatement
import dinesh.parmar.skills.core.ProblemStatementBuilder
import dinesh.parmar.skills.core.SolveProblems

class PalindromeNumber : Problem<Int, Boolean>() {
    override val problemStatement: ProblemStatement<Int, Boolean>
        get() = ProblemStatementBuilder(problem = SolveProblems.Palindrome)
            .description(desc = "Given an integer x, return true if x is a palindrome, and false otherwise.\n")
            .example(
                explanation = "121 reads as 121 from left to right and from right to left.",
                input = 121,
                output = true
            )
            .example(
                explanation = "From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome",
                input = -121,
                output = false
            )
            .example(
                explanation = "Reads 01 from right to left. Therefore it is not a palindrome.",
                input = 10,
                output = false
            )
            .constraints("-231 <= x <= 231 - 1")
            .build()

    override fun solve(input: Int): Boolean {
        // If number is negative like -121
        // it cannot be palindrome because reverse becomes 121-
        if (input < 0) return false

        // If number ends with 0 like 10, 120
        // it cannot be palindrome
        // because reverse becomes 01, 021
        // Exception: 0 itself is palindrome
        if (input != 0 && input % 10 == 0) return false

        // This variable will store reversed half of number
        // Example:
        // 12321 -> reversed becomes 12 then 123
        var reversed = 0

        // Copy original number into another variable
        // because we will modify it
        var original = input

        // Loop until original half becomes smaller
        // than reversed half
        //
        // Example:
        // original = 12321
        // reversed = 0
        while (original > reversed) {

            // Get last digit using modulo (%)
            //
            // Example:
            // 12321 % 10 = 1
            // 1232 % 10 = 2
            val digit = original % 10

            // Add digit into reversed number
            //
            // Example:
            // reversed = 0 * 10 + 1 = 1
            // reversed = 1 * 10 + 2 = 12
            reversed = reversed * 10 + digit

            // Remove last digit from original number
            //
            // Example:
            // 12321 / 10 = 1232
            // 1232 / 10 = 123
            //
            // Integer division removes decimal part
            original /= 10
        }



        return original == reversed
                // Even digits case:
                // 1221
                // original = 12
                // reversed = 12
                ||
                // Odd digits case:
                // 12321
                // original = 12
                // reversed = 123
                //
                // Middle digit (3) does not matter
                // so remove it using reversed / 10
                //
                // 123 / 10 = 12
                original == reversed / 10
    }

}