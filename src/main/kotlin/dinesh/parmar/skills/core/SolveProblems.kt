package dinesh.parmar.skills.core


data class ProblemChallenge(
    val pId: String,
    val title: String,
    val recordFile : String = ""
) {

    fun fileName() = (if (recordFile.isBlank()) "${pId}_${title.trim()}" else "${pId}_$recordFile")
        .replace(" ","_")
        .trim()
        .plus(".md")
}


enum class SolveProblems(val challenge: ProblemChallenge) {
    SeparateDigits(ProblemChallenge("2553", "Separate the Digits in an Array","separateDigits")),
    Palindrome(ProblemChallenge("9", "Palindrome Number", "palindrome"))
}