package dinesh.parmar.skills.problems

import builder.ProblemStatementBuilder
import dinesh.parmar.skills.models.ListNode
import dp.problemsovle.skills.framework.model.*

class Practice : Problem<Pair<ListNode?,ListNode?>, ListNode?>()  {


    override val metadata: ProblemMetadata
        get() = ProblemMetadata(
            id = 0,
            title = "Practice",
            difficulty = Difficulty.EASY,
            tags = setOf(),
            url = ""
        )

    override val statement: ProblemStatement<Pair<ListNode?,ListNode?>, ListNode?> =
        ProblemStatementBuilder<Pair<ListNode?,ListNode?>, ListNode?>()
            .description(
                "Practice"
            )
            .example(
                explanation = "",
                input = Pair(
                    ListNode(1).apply {
                        next = ListNode(2).apply {
                            next = ListNode(4)
                        }
                    },
                    ListNode(1).apply {
                        next = ListNode(3).apply {
                            next = ListNode(4)
                        }
                    }),
                expected = ListNode(1).apply {
                    next = ListNode(1).apply {
                        next = ListNode(2).apply {
                            next = ListNode(3).apply {
                                next = ListNode(4).apply {
                                    next = ListNode(4)
                                }
                            }
                        }
                    }
                }
            )
            .build()

    override fun solve(input: Pair<ListNode?,ListNode?>): ListNode? {
        var tvL1 = input.first
        var tvL2 = input.second

        val result = ListNode(-1)
        var traverse = result

        while (tvL1!=null && tvL2!=null){

            if (tvL1.value <= tvL2.value){
                traverse.next = tvL1
                tvL1 = tvL1.next
            }else {
                traverse.next = tvL2
                tvL2 = tvL2.next
            }
            traverse = traverse.next!!
        }

        if (tvL1!=null){
            traverse.next = tvL1
        }

        if (tvL2!=null){
            traverse.next = tvL2
        }

        return result.next!!
    }
}