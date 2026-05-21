package dinesh.parmar.skills.problems.strings

import builder.ProblemStatementBuilder
import dp.problemsovle.skills.framework.model.*

class ValidParantheses : Problem<String,Boolean>(){

    /**
     * Problem metadata used for registry look-up, documentation, and README table.
     */
    override val metadata: ProblemMetadata = ProblemMetadata(
        id = 20,
        title = "Valid Parentheses",
        difficulty = Difficulty.MEDIUM,
        tags = setOf(Tag.STRING,Tag.STACK),
        url = "https://leetcode.com/problems/valid-parentheses/"
    )

    /**
     * Problem statement with description, worked examples, and constraints.
     * Built via [ProblemStatementBuilder] to keep the DSL clean and validated.
     */
    override val statement: ProblemStatement<String,Boolean> =
        ProblemStatementBuilder<String,Boolean>()
            .description(
                """
                Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.

                An input string is valid if:

                Open brackets must be closed by the same type of brackets.
                Open brackets must be closed in the correct order.
                Every close bracket has a corresponding open bracket of the same type.
                """.trimIndent()
            )
            .example(
                explanation = "",
                input = "()",
                expected = true
            )
            .example(
                explanation = "",
                input = "()[]{}",
                expected = true
            )
            .example(
                explanation = "",
                input = "(]",
                expected = false
            )
            .example(
                explanation = "",
                input = "([])",
                expected = true
            )
            .example(
                explanation = "",
                input = "([)]",
                expected = false
            )
            .constraints(
                "1 <= s.length <= 10^4",
                "s consists of parentheses only '()[]{}'."
            )
            .build()

    override fun solve(input: String): Boolean {

        // Create a stack to keep track of opening brackets
        val stack = mutableListOf<Char>()
        // Map each opening bracket to its corresponding closing bracket
        val map = mapOf('(' to ')', '{' to '}', '[' to ']')

        // Loop through each character in the string
        for (char in input) {
            if (char in map.keys) {
                // If it's an opening bracket, push it onto the stack
                stack.add(char)
            } else if (stack.isNotEmpty() && map[stack.last()] == char) {
                // If it's a closing bracket and it matches the top of the stack, pop the top of the stack
                stack.removeAt(stack.size - 1)
            } else {
                // If it's a mismatched closing or stack is empty, return false
                return false
            }
        }

        // After processing, if the stack is empty, all brackets were matched
        return stack.isEmpty()
    }


    /**
     * Kotlin implementation snippet used for README/MD generation.
     */
    override val sourceCode: String
        get() = """
    override fun solve(input: String): Boolean {

        // Create a stack to keep track of opening brackets
        val stack = mutableListOf<Char>()
        // Map each opening bracket to its corresponding closing bracket
        val map = mapOf('(' to ')', '{' to '}', '[' to ']')

        // Loop through each character in the string
        for (char in input) {
            if (char in map.keys) {
                // If it's an opening bracket, push it onto the stack
                stack.add(char)
            } else if (stack.isNotEmpty() && map[stack.last()] == char) {
                // If it's a closing bracket and it matches the top of the stack, pop the top of the stack
                stack.removeAt(stack.size - 1)
            } else {
                // If it's a mismatched closing or stack is empty, return false
                return false
            }
        }

        // After processing, if the stack is empty, all brackets were matched
        return stack.isEmpty()
    }
    """.trimIndent()

}