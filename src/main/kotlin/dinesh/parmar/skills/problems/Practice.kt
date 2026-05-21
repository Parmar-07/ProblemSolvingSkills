package dinesh.parmar.skills.problems

import builder.ProblemStatementBuilder
import dp.problemsovle.skills.framework.model.*

class Practice : Problem<String, Boolean>()  {
    override val metadata: ProblemMetadata
        get() = ProblemMetadata(
            id = 0,
            title = "Practice",
            difficulty = Difficulty.EASY,
            tags = setOf(),
            url = ""
        )

    override val statement: ProblemStatement<String, Boolean> =
        ProblemStatementBuilder<String, Boolean>()
            .description(
                "Practice"
            )
            .example(
                explanation = "",
                input = "(]",
                expected = false
            )
            .build()

    override fun solve(input: String): Boolean {

        val stack = mutableListOf<Char>()
        val mapKeys = mapOf('(' to ')', '[' to ']', '{' to '}')


        for(char in input) {

            if (char in mapKeys.keys)
            {
                stack.add(char)
            } else if (stack.isNotEmpty() && mapKeys[stack.last()] == char){
                stack.removeAt(stack.size -1)
            }else{
                return false
            }
        }

        return stack.isEmpty()
    }
}