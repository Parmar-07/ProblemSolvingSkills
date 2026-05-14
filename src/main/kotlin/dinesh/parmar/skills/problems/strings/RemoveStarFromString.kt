package dinesh.parmar.skills.problems.strings

import builder.ProblemStatementBuilder
import dp.problemsovle.skills.framework.model.*

class RemoveStarFromString : Problem<String,String>(){

    /**
     * Problem metadata used for registry look-up, documentation, and README table.
     */
    override val metadata: ProblemMetadata = ProblemMetadata(
        id = 2390,
        title = "Removing Stars From a String",
        difficulty = Difficulty.MEDIUM,
        tags = setOf(Tag.STRING,Tag.STACK),
        url = "https://leetcode.com/problems/removing-stars-from-a-string/"
    )

    /**
     * Problem statement with description, worked examples, and constraints.
     * Built via [ProblemStatementBuilder] to keep the DSL clean and validated.
     */
    override val statement: ProblemStatement<String,String> =
        ProblemStatementBuilder<String,String>()
            .description(
                """
                You are given a string s, which contains stars *.

                In one operation, you can:

                Choose a star in s.
                Remove the closest non-star character to its left, as well as remove the star itself.
                Return the string after all stars have been removed.

                Note:

                The input will be generated such that the operation is always possible.
                It can be shown that the resulting string will always be unique.
                """.trimIndent()
            )
            .example(
                explanation = """
                    Performing the removals from left to right:
                    - The closest character to the 1st star is 't' in "leet**cod*e". s becomes "lee*cod*e".
                    - The closest character to the 2nd star is 'e' in "lee*cod*e". s becomes "lecod*e".
                    - The closest character to the 3rd star is 'd' in "lecod*e". s becomes "lecoe".
                    There are no more stars, so we return "lecoe"
                """.trimIndent(),
                input = "leet**cod*e",
                expected = "lecoe"
            )
            .example(
                explanation = "The entire string is removed, so we return an empty string.",
                input = "erase*****",
                expected = "")
            .constraints(
                "1 <= s.length <= 10^5",
                "s consists of lowercase English letters and stars *.",
                "The operation above can be performed on s."
            )
            .build()

    override fun solve(input: String): String {

        // Create a stack (double-ended queue) to hold the characters
        val stack = ArrayDeque<Char>()

        // Iterate through each character in the string 'input'
        for (i in input.indices) { // 'i' is the index of each character
            val ch = input[i] // Current character

            if (ch == '*') {
                // If we see a star
                if (stack.isNotEmpty()) {
                    // If stack is not empty, pop the top character
                    stack.removeLast() // Remove the closest left character
                }
                // Else: If stack is empty, do nothing (no letter to remove)
            } else {
                // If it’s a letter
                stack.addLast(ch) // Push the current letter onto the stack
            }

            // After each character, print index and stack state for clarity
            // Example with input "leet**cod*e":
            // i = 0: ch = 'l', stack = ['l'] (push 'l')
            // i = 1: ch = 'e', stack = ['l', 'e'] (push 'e')
            // i = 2: ch = 'e', stack = ['l', 'e', 'e'] (push 'e')
            // i = 3: ch = 't', stack = ['l', 'e', 'e', 't'] (push 't')
            // i = 4: ch = '', stack = ['l', 'e', 'e'] (pop 't')
            // i = 5: ch = '', stack = ['l', 'e'] (pop 'e')
            // i = 6: ch = 'c', stack = ['l', 'e', 'c'] (push 'c')
            // i = 7: ch = 'o', stack = ['l', 'e', 'c', 'o'] (push 'o')
            // i = 8: ch = 'd', stack = ['l', 'e', 'c', 'o', 'd'] (push 'd')
            // i = 9: ch = '*', stack = ['l', 'e', 'c', 'o'] (pop 'd')
            // i = 10: ch = 'e', stack = ['l', 'e', 'c', 'o', 'e'] (push 'e')

        }

        // After processing all characters, we join them to form the result
        return stack.joinToString("") // Join
    }


    /**
     * Kotlin implementation snippet used for README/MD generation.
     */
    override val sourceCode: String
        get() = """
    override fun solve(input: String): String {

        // Create a stack (double-ended queue) to hold the characters
        val stack = ArrayDeque<Char>()

        // Iterate through each character in the string 'input'
        for (i in input.indices) { // 'i' is the index of each character
            val ch = input[i] // Current character

            if (ch == '*') {
                // If we see a star
                if (stack.isNotEmpty()) {
                    // If stack is not empty, pop the top character
                    stack.removeLast() // Remove the closest left character
                }
                // Else: If stack is empty, do nothing (no letter to remove)
            } else {
                // If it’s a letter
                stack.addLast(ch) // Push the current letter onto the stack
            }

            // After each character, print index and stack state for clarity
            // Example with input "leet**cod*e":
            // i = 0: ch = 'l', stack = ['l'] (push 'l')
            // i = 1: ch = 'e', stack = ['l', 'e'] (push 'e')
            // i = 2: ch = 'e', stack = ['l', 'e', 'e'] (push 'e')
            // i = 3: ch = 't', stack = ['l', 'e', 'e', 't'] (push 't')
            // i = 4: ch = '', stack = ['l', 'e', 'e'] (pop 't')
            // i = 5: ch = '', stack = ['l', 'e'] (pop 'e')
            // i = 6: ch = 'c', stack = ['l', 'e', 'c'] (push 'c')
            // i = 7: ch = 'o', stack = ['l', 'e', 'c', 'o'] (push 'o')
            // i = 8: ch = 'd', stack = ['l', 'e', 'c', 'o', 'd'] (push 'd')
            // i = 9: ch = '*', stack = ['l', 'e', 'c', 'o'] (pop 'd')
            // i = 10: ch = 'e', stack = ['l', 'e', 'c', 'o', 'e'] (push 'e')

        }

        // After processing all characters, we join them to form the result
        return stack.joinToString("") // Join
    }
    """.trimIndent()

}