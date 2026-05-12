package dinesh.parmar.skills.core

import java.io.File


/**
 *
 */
abstract class Problem<I, O> {


    abstract val problemStatement : ProblemStatement<I, O>
    abstract fun solve(input: I): O


    fun test(record: Boolean = false) {
        println(problemStatement.render())
        problemStatement.examples.forEachIndexed { i, e ->
            e.render(i, ::solve)
        }

        if (record)
          recordStatement(problemStatement)
    }


   private fun recordStatement(problemStatement: ProblemStatement<*,*>) {

        val fileName = problemStatement.problem.challenge.fileName()


        val file = File("src/main/resources/$fileName")
        if (!file.exists())
        file.createNewFile()

        file.writeText(problemStatement.render(false))

    }



}