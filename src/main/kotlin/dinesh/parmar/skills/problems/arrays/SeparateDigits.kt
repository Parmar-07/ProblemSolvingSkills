package dinesh.parmar.skills.problems.arrays

import dinesh.parmar.skills.core.Problem
import dinesh.parmar.skills.core.ProblemStatement
import dinesh.parmar.skills.core.ProblemStatementBuilder
import dinesh.parmar.skills.core.SolveProblems

class SeparateDigits : Problem<IntArray, IntArray>() {
    override val problemStatement: ProblemStatement<IntArray, IntArray>
        get() = ProblemStatementBuilder(problem = SolveProblems.SeparateDigits)
            .description(
                desc = "Given an array of positive integers nums, return an array answer that consists of the digits of each integer in nums after separating them in the same order they appear in nums.\n" +
                        "\n" +
                        "To separate the digits of an integer is to get all the digits it has in the same order.\n" +
                        "\n" +
                        "For example, for the integer 10921, the separation of its digits is [1,0,9,2,1]."
            )
            .example(
                explanation = "- The separation of 13 is [1,3].\n" +
                        "- The separation of 25 is [2,5].\n" +
                        "- The separation of 83 is [8,3].\n" +
                        "- The separation of 77 is [7,7].\n\n" +
                        "answer = [1,3,2,5,8,3,7,7]. \n\n" +
                        "Note that answer contains the separations in the same order",
                input = intArrayOf(13,25,89,77),
                output = intArrayOf(1,3,2,5,8,9,7,7))
            .example(
                explanation = "The separation of each integer in nums is itself.",
                input = intArrayOf(7,1,3,9),
                output = intArrayOf(7,1,3,9))
            .constraints("1 <= nums.length <= 1000")
            .constraints("1 <= nums[i] <= 105")
            .build()

    override fun solve(input: IntArray): IntArray {

        // Create final result list
        val result = mutableListOf<Int>()

        // input = [13, 25, 83, 77]
        // First num = 13
        for (num in input) {

            // Create copy because original num should remain unchanged
            // n = 13
            var n = num

            // Temporary list to store digits of current number
            // Current number digits यहाँ store होंगे
            val digits = mutableListOf<Int>()

            // Continue until number becomes 0
            // n = 13 > 0 → true
            while (n > 0) {

                // Extract last digit using modulo
                // 13 % 10 = 3
                // digit = 3
                val digit = n % 10

                // Add extracted digit into digits list
                // digits = [3]
                digits.add(digit)

                // Remove last digit from number
                // 13 / 10 = 1
                // n = 1
                n /= 10

                /*
                    DRY RUN

                    First Iteration
                    ----------------
                    n = 13
                    digit = 13 % 10 = 3
                    digits = [3]
                    n = 1

                    Second Iteration
                    ----------------
                    n = 1
                    digit = 1 % 10 = 1
                    digits = [3, 1]
                    n = 0

                    Loop stops because n > 0 is false
                */
            }

            // Digits are reversed because we extracted from last
            // digits = [3,1]
            // reversed() = [1,3]
            for (digit in digits.reversed()) {

                // Add digits into final result list
                // result = [1,3]
                result.add(digit)

                /*
                    DRY RUN

                    digit = 1
                    result = [1]

                    digit = 3
                    result = [1,3]
                */
            }

            /*
                AFTER FIRST NUMBER (13)

                result = [1,3]
            */
        }

        // Convert mutable list into IntArray
        // Final Output:
        // [1,3,2,5,8,3,7,7]
        return result.toIntArray()
    }
}