package dinesh.parmar.skills.core


data class ProblemStatement<I,O>(
    val problem : SolveProblems,
    val description: String,
    val examples: List<Example<I, O>>,
    val constraints: List<String>
) {


    @Suppress("IMPLICIT_CAST_TO_ANY")
    fun render(drawLines: Boolean = true) = buildString {

        this drawLineInConsole drawLines
        appendLine("${problem.challenge.pId}. ${problem.challenge.title}")
        drawLineInConsole(drawLines)
        appendLine()
        appendLine("Description:\n")
        appendLine(description)
        appendLine()

        examples.forEachIndexed { i ,e ->
            appendLine("Example${i+1} :")
            appendLine()
            val input = if (e.testCases.input is IntArray) e.testCases.input.contentToString() else e.testCases.input
            val output = if (e.testCases.output is IntArray) e.testCases.output.contentToString() else e.testCases.output
            appendLine("Explanation: ${e.explanation}")
            appendLine("\nInput: $input")
            appendLine("\nOutput: $output")
            appendLine()
        }

        appendLine("\nConstraints:")
        constraints.forEach {
            appendLine("$it\n")
        }

        this drawLineInConsole drawLines
        this drawLineInConsole drawLines

        appendLine()

    }

    private infix fun StringBuilder.drawLineInConsole(drawLines: Boolean) {
        if (drawLines)
            appendLine("==============================================")

    }



}
