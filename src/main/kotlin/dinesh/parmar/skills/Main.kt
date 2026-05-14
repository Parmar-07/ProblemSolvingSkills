package dinesh.parmar.skills

import dinesh.parmar.skills.problems.arrays.ProductItSelf
import dinesh.parmar.skills.problems.arrays.SeparateDigits
import dinesh.parmar.skills.problems.numbers.PalindromeNumber
import dinesh.parmar.skills.problems.strings.RemoveStarFromString
import dp.problemsovle.skills.framework.registry.ProblemRegistry
import dp.problemsovle.skills.framework.runner.ProblemRunner
import kotlin.system.exitProcess


/**
 * Application entry point for the Competitive Programming Framework.
 *
 * ## How to Add a New Problem
 * 1. Create a class in the appropriate `problems/<category>/` package extending `Problem<I, O>`.
 * 2. Register it here with `ProblemRegistry.register(YourProblem())`.
 * 3. Run `main()` — tests execute, docs generate, README updates automatically.
 *
 * ## Running Options
 * - `ProblemRunner.runAll()` — run every registered problem
 * - `ProblemRunner.run(SomeProblem())` — run a single problem
 * - `ProblemRunner.runCi(SomeProblem())` — CI mode: exits with code 1 on failure
 */
fun main() {
    // ── Register all problems ──────────────────────────────────────────────────
    // Add new problems here. Order determines README table insertion order.
    ProblemRegistry.registerAll(
        PalindromeNumber(),
        SeparateDigits(),
        ProductItSelf(),
        RemoveStarFromString()
    )


    // ── Run specific problem by Id ────────────────────────────────────────────
    ProblemRegistry.getById(2390)?.let { ProblemRunner.runCi(it)  }


    /*
    // ── Run all registered problems ────────────────────────────────────────────
    // Passing problems automatically generate .md docs and update README.md
    val allPassed = ProblemRunner.runAll(generateDocs = true)

    // ── CI exit code ───────────────────────────────────────────────────────────
    if (!allPassed) {
        exitProcess(1)
    }*/


}
