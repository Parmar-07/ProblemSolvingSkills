package dinesh.parmar.skills.problems.arrays

import builder.ProblemStatementBuilder
import dinesh.parmar.skills.input.TwoSum
import dp.problemsovle.skills.framework.model.*

class TwoSumEqualsTargetFindIndexes : Problem<TwoSum, IntArray>()  {


    override val metadata: ProblemMetadata
        get() = ProblemMetadata(
            id = 1,
            title = "Two Sum",
            difficulty = Difficulty.EASY,
            tags = setOf(Tag.ARRAY, Tag.MATH, Tag.HASH_MAP),
            url = "https://leetcode.com/problems/two-sum/"
        )

    override val statement: ProblemStatement<TwoSum, IntArray> =
        ProblemStatementBuilder<TwoSum, IntArray>()
            .description(
                "Given an array of integers array and an integer target, return indices of the two numbers such that they add up to target.\n" +
                        "\n" +
                        "You may assume that each input would have exactly one solution, and you may not use the same element twice.\n" +
                        "\n" +
                        "You can return the answer in any order."
            )
            .example(
                explanation = "Because array[0] + array[1] == 9, we return [0, 1].",
                input = TwoSum(intArrayOf(2,7,11,5),9),
                expected = intArrayOf(0,1)
            )
            .example(
                explanation = "Because array[1] + array[2] == 6, we return [1, 2].",
                input = TwoSum(intArrayOf(3,2,4),6),
                expected = intArrayOf(1,2)
            )
            .example(
                explanation = "Because array[0] + array[1] == 6, we return [0, 1].",
                input = TwoSum(intArrayOf(3,3),6),
                expected = intArrayOf(0,1)
            )
            .constraints("2 <= array.length <= 10^4")
            .constraints("-10^9 <= array[i] <= 10^9")
            .constraints("-10^9 <= target <= 10^9")
            .build()

    override fun solve(input: TwoSum): IntArray {

        // HashMap:
        // number -> index
        val mapIndexValue = hashMapOf<Int,Int>()

        // inputs (array, target)
        val array = input.array
        val target = input.target

        // Loop through array indexes
        for (index in array.indices) {

            // Current number from index
            val current = array[index]

            // Number needed to reach target
            val need =  target - current

            // Check if needed number already exists in stored map value
            if (mapIndexValue.containsKey(need)){
                // return indexes
                return intArrayOf(mapIndexValue[need]!!,index)
            }

            // Store current number and index in map
            mapIndexValue[current] = index
        }

        // If not found target return empty array
        return intArrayOf()
    }

    override val sourceCode: String
        get() = """
    override fun solve(input: TwoSum): IntArray {

        // HashMap:
        // number -> index
        val mapIndexValue = hashMapOf<Int,Int>()

        // inputs (array, target)
        val array = input.array
        val target = input.target

        // Loop through array indexes
        for (index in array.indices) {

            // Current number from index
            val current = array[index]

            // Number needed to reach target
            val need =  target - current

            // Check if needed number already exists in stored map value
            if (mapIndexValue.containsKey(need)){
                // return indexes
                return intArrayOf(mapIndexValue[need]!!,index)
            }

            // Store current number and index in map
            mapIndexValue[current] = index
        }

        // If not found target return empty array
        return intArrayOf()
    }       
        """.trimIndent()

}