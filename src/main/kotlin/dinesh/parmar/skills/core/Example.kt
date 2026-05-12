package dinesh.parmar.skills.core

/**
 *
 *
 * */
data class Example<I,O>(
    val explanation : String,
    val testCases : TestCase<I, O>
) {

    /**
     * 
     * */
    @Suppress("IMPLICIT_CAST_TO_ANY")
    fun render(index: Int, solve: (I)->O) = buildString {
        val testCase = "TestCase${index + 1}:"
        println(testCase)
        println("-------------------------------------------")


        val input = if (testCases.input is IntArray) testCases.input.contentToString() else testCases.input
        val result = solve(testCases.input)
        val expectedResult = testCases.output

        val actualValue = if (result is IntArray) result.contentToString() else result
        val expectedValue = if (expectedResult is IntArray) expectedResult.contentToString() else expectedResult

        println("Input: $input")
        println("Actual: $actualValue")
        println("Expected: $expectedValue")

        if (actualValue == expectedValue) {
            println("PASSED")
        } else {
            println("FAILED")
            throw AssertionError(
                "❌ $testCase FAILED." +
                        "\nInput: $input" +
                        "\nActual: $actualValue" +
                        "\nExpected: $expectedValue"
            )
        }

        println("-------------------------------------------")

    }

}
