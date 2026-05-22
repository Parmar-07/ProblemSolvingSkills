package dinesh.parmar.skills.problems

import builder.ProblemStatementBuilder
import dinesh.parmar.skills.input.TwoSum
import dp.problemsovle.skills.framework.model.*

class Practice : Problem<TwoSum, IntArray>()  {


    override val metadata: ProblemMetadata
        get() = ProblemMetadata(
            id = 0,
            title = "Practice",
            difficulty = Difficulty.EASY,
            tags = setOf(),
            url = ""
        )

    override val statement: ProblemStatement<TwoSum, IntArray> =
        ProblemStatementBuilder<TwoSum, IntArray>()
            .description(
                "Practice"
            )
            .example(
                explanation = "",
                input = TwoSum(
                    intArrayOf(2,7,11,5),9),
                expected = intArrayOf(0,1)
            )
            .build()

    override fun solve(input: TwoSum): IntArray {
        val mapIndexValue = hashMapOf<Int,Int>()

        val array = input.array
        val target = input.target

        for (index in array.indices) {

            val current = array[index]
            val need =  target - current

            if (mapIndexValue.containsKey(need)){
                return intArrayOf(mapIndexValue[need]!!,index)
            }

            mapIndexValue[current] = index
        }

        return intArrayOf()
    }
}