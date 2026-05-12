package dinesh.parmar.skills.core

class ProblemStatementBuilder(private val problem : SolveProblems){

private var description: String = ""
private var examples = mutableListOf<Example<*, *>>()
private var constraints = mutableListOf<String>()

    fun description(desc: String) = apply { description = desc }


    fun <I,O> example(explanation : String, input : I, output: O) = apply {
        examples.add(
            Example(
            explanation,
            TestCase(input, output)
        )
        )
    }

    fun constraints(constraint: String) = apply { constraints.add(constraint) }

    @Suppress("UNCHECKED_CAST")
    fun<I,O> build() : ProblemStatement<I, O> {
        require(description.isNotBlank()){ "Description required" }
        return ProblemStatement(
            problem = problem,
            description = description,
            examples = examples as List<Example<I, O>>,
            constraints = constraints
        )
    }
}