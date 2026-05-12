package dinesh.parmar.skills.core

import dinesh.parmar.skills.problems.arrays.SeparateDigits
import dinesh.parmar.skills.problems.numbers.PalindromeNumber
import java.io.IOException

class ProblemTestRule {

    private val solveProblems by lazy {
        hashMapOf<SolveProblems, Problem<*, *>>().apply {
            put(SolveProblems.Palindrome, PalindromeNumber())
            put(SolveProblems.SeparateDigits, SeparateDigits())
        }
    }
    
    fun testProblem(problem: SolveProblems){
        val code = solveProblems[problem] ?: throw IOException("No ${problem.challenge} Problem found!")
        code.test(true)
    }

    fun testProblems() {
        solveProblems.forEach { (k, p) ->
            println("Testing.....${k.challenge}\n\n")
            p.test() }

    }

}