package dinesh.parmar.skills.problems.arrays

import builder.ProblemStatementBuilder
import dp.problemsovle.skills.framework.model.*

class ProductItSelf : Problem<IntArray,IntArray>(){

    /**
     * Problem metadata used for registry look-up, documentation, and README table.
     */
    override val metadata: ProblemMetadata = ProblemMetadata(
        id = 238,
        title = "Product of Array Except Self",
        difficulty = Difficulty.MEDIUM,
        tags = setOf(Tag.ARRAY),
        url = "https://leetcode.com/problems/product-of-array-except-self/"
    )

    /**
     * Problem statement with description, worked examples, and constraints.
     * Built via [ProblemStatementBuilder] to keep the DSL clean and validated.
     */
    override val statement: ProblemStatement<IntArray, IntArray> =
        ProblemStatementBuilder<IntArray, IntArray>()
            .description(
                """
                Given an integer array nums, return an array answer such that answer[i] is equal to the product of all the elements of nums except nums[i].

                The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.

                You must write an algorithm that runs in O(n) time and without using the division operation.
                """.trimIndent()
            )
            .example(
                explanation = "",
                input = intArrayOf(1,2,3,4),
                expected = intArrayOf(24,12,8,6)
            )
            .example(
                explanation = "",
                input = intArrayOf(-1,1,0,-3,3),
                expected = intArrayOf(0,0,9,0,0)
            )
            .constraints(
                "2 <= nums.length <= 10^5",
                "-30 <= nums[i] <= 30",
                "The input is generated such that answer[i] is guaranteed to fit in a 32-bit integer."
            )
            .inputFormat("An array of positive integers.")
            .outputFormat("An array of individual digits in the original order.")
            .build()

    override fun solve(input: IntArray): IntArray {

        val n = input.size // Input: input = [1, 2, 3, 4], so n = 4
        val answer = IntArray(n) // Create answer array of size 4
        var prefixProduct = 1 // Prefix product starts at 1

        // First loop: compute prefix products
        for (i in input.indices) { // i = 0 to 3
            // Example dry run with input [1, 2, 3, 4]:
            // i = 0: prefixProduct = 1
            // answer[0] = 1 (store prefixProduct)
            // prefixProduct = 1 * input[0]
            answer[i] = prefixProduct  // answer[0] = 1
            prefixProduct *= input[i] // prefixProduct = 1

            // i = 1: prefixProduct = 1
            // answer[1] = 1 (store prefixProduct)
            // prefixProduct = 1 * input[1] = 2
            //answer[1] = 1
            //prefixProduct = 2

            // i = 2: prefixProduct = 2
            // answer[2] = 2 (store prefixProduct)
            // prefixProduct = 2 * input[2] = 6
            //answer[2] = 2
            //prefixProduct = 6

            // i = 3: prefixProduct = 6
            // answer[3] = 6 (store prefixProduct)
            // prefixProduct = 6 * input[3] = 24
            //answer[3] = 6
        }


        var suffixProduct = 1 // Suffix product starts at 1

        // Second loop: compute suffix products
        for (i in input.indices.reversed()) { // i = 3 to 0

            answer[i] *= suffixProduct // answer[3] = answer[3] * suffixProduct = 6 * 1 = 6
            suffixProduct *= input[i] //1 * input[3] = 4

            // Example dry run:
            // i = 3: suffixProduct = 1
            // answer[3] = answer[3] * suffixProduct = 6 * 1 = 6
            // suffixProduct = 1 * input[3] = 4

            // i = 2: suffixProduct = 4
            // answer[2] = answer[2] * suffixProduct = 2 * 4 = 8
            // suffixProduct = 4 * input[2] = 12

            // i = 1: suffixProduct = 12
            // answer[1] = answer[1] * suffixProduct = 1 * 12 = 12
            // suffixProduct = 12 * input[1] = 24

            // i = 0: suffixProduct = 24
            // answer[0] = answer[0] * suffixProduct = 1 * 24 = 24
            // suffixProduct = 24 * input[0] = 24
        }

        return answer
    }


    /**
     * Kotlin implementation snippet used for README/MD generation.
     */
    override val sourceCode: String
        get() = """
        override fun solve(input: IntArray): IntArray {

        val n = input.size // Input: input = [1, 2, 3, 4], so n = 4
        val answer = IntArray(n) // Create answer array of size 4
        var prefixProduct = 1 // Prefix product starts at 1

        // First loop: compute prefix products
        for (i in input.indices) { // i = 0 to 3
            // Example dry run with input [1, 2, 3, 4]:
            // i = 0: prefixProduct = 1
            // answer[0] = 1 (store prefixProduct)
            // prefixProduct = 1 * input[0]
            answer[i] = prefixProduct  // answer[0] = 1
            prefixProduct *= input[i] // prefixProduct = 1

            // i = 1: prefixProduct = 1
            // answer[1] = 1 (store prefixProduct)
            // prefixProduct = 1 * input[1] = 2
            //answer[1] = 1
            //prefixProduct = 2

            // i = 2: prefixProduct = 2
            // answer[2] = 2 (store prefixProduct)
            // prefixProduct = 2 * input[2] = 6
            //answer[2] = 2
            //prefixProduct = 6

            // i = 3: prefixProduct = 6
            // answer[3] = 6 (store prefixProduct)
            // prefixProduct = 6 * input[3] = 24
            //answer[3] = 6
        }


        var suffixProduct = 1 // Suffix product starts at 1

        // Second loop: compute suffix products
        for (i in input.indices.reversed()) { // i = 3 to 0

            answer[i] *= suffixProduct // answer[3] = answer[3] * suffixProduct = 6 * 1 = 6
            suffixProduct *= input[i] //1 * input[3] = 4

            // Example dry run:
            // i = 3: suffixProduct = 1
            // answer[3] = answer[3] * suffixProduct = 6 * 1 = 6
            // suffixProduct = 1 * input[3] = 4

            // i = 2: suffixProduct = 4
            // answer[2] = answer[2] * suffixProduct = 2 * 4 = 8
            // suffixProduct = 4 * input[2] = 12

            // i = 1: suffixProduct = 12
            // answer[1] = answer[1] * suffixProduct = 1 * 12 = 12
            // suffixProduct = 12 * input[1] = 24

            // i = 0: suffixProduct = 24
            // answer[0] = answer[0] * suffixProduct = 1 * 24 = 24
            // suffixProduct = 24 * input[0] = 24
            }

            return answer
         }
    """.trimIndent()

}