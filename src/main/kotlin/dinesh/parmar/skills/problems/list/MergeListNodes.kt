package dinesh.parmar.skills.problems.list

import builder.ProblemStatementBuilder
import dinesh.parmar.skills.input.ListNode
import dp.problemsovle.skills.framework.model.*

class MergeListNodes : Problem<Pair<ListNode?,ListNode?>, ListNode?>()  {


    override val metadata: ProblemMetadata
        get() = ProblemMetadata(
            id = 21,
            title = "Merge Two Sorted Lists",
            difficulty = Difficulty.EASY,
            tags = setOf(Tag.SORTING,Tag.LINKED_LIST),
            url = "https://leetcode.com/problems/merge-two-sorted-lists"
        )

    override val statement: ProblemStatement<Pair<ListNode?,ListNode?>, ListNode?> =
        ProblemStatementBuilder<Pair<ListNode?,ListNode?>, ListNode?>()
            .description(
                "You are given the heads of two sorted linked lists list1 and list2.\n" +
                        "\n" +
                        "Merge the two lists into one sorted list. The list should be made by splicing together the nodes of the first two lists.\n" +
                        "\n" +
                        "Return the head of the merged linked list."
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
            .example(
                explanation = "",
                input = Pair(null,null),
                expected = null
            )
            .example(
                explanation = "",
                input = Pair(null,ListNode(0)),
                expected = ListNode(0)
            )
            .constraints("The number of nodes in both lists is in the range [0, 50].")
            .constraints("-100 <= Node.val <= 100")
            .constraints("Both list1 and list2 are sorted in non-decreasing order.")
            .build()

    override fun solve(input: Pair<ListNode?,ListNode?>): ListNode? {
        // Pointer for traversing list1
        var tvL1 = input.first
        // Pointer for traversing list2
        var tvL2 = input.second

        // Dummy node helps simplify edge cases
        // Final merged list will start from result.next
        val result = ListNode(-1)
        // Current traverse used to build merged list
        var traverse = result

        // Traverse both lists until one becomes empty
        while (tvL1 != null && tvL2 != null){

            // Compare values from both lists
            if (tvL1.value <= tvL2.value){
                // Attach smaller node from list1
                traverse.next = tvL1
                // Move tvL1 forward
                tvL1 = tvL1.next
            }else {
                // Attach smaller node from list2
                traverse.next = tvL2
                // Move tvL2 forward
                tvL2 = tvL2.next
            }
            traverse = traverse.next!!
        }

        // If tvL1 still has remaining nodes,
        // attach them directly
        if (tvL1 != null){
            traverse.next = tvL1
        }

        // If tvL2 still has remaining nodes,
        // attach them directly
        if (tvL2 != null){
            traverse.next = tvL2
        }
        // Return merged list
        // Skip result node which is -1
        return result.next
    }

    override val sourceCode: String
        get() = """
    override fun solve(input: Pair<ListNode?,ListNode?>): ListNode? {
        // Pointer for traversing list1
        var tvL1 = input.first
        // Pointer for traversing list2
        var tvL2 = input.second

        // Dummy node helps simplify edge cases
        // Final merged list will start from result.next
        val result = ListNode(-1)
        // Current traverse used to build merged list
        var traverse = result

        // Traverse both lists until one becomes empty
        while (tvL1 != null && tvL2 != null){

            // Compare values from both lists
            if (tvL1.value <= tvL2.value){
                // Attach smaller node from list1
                traverse.next = tvL1
                // Move tvL1 forward
                tvL1 = tvL1.next
            }else {
                // Attach smaller node from list2
                traverse.next = tvL2
                // Move tvL2 forward
                tvL2 = tvL2.next
            }
            traverse = traverse.next!!
        }

        // If tvL1 still has remaining nodes,
        // attach them directly
        if (tvL1 != null){
            traverse.next = tvL1
        }

        // If tvL2 still has remaining nodes,
        // attach them directly
        if (tvL2 != null){
            traverse.next = tvL2
        }
        // Return merged list
        // Skip result node which is -1
        return result.next!!
    }
        """.trimIndent()
}