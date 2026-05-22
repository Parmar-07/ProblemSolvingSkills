# 21. Merge Two Sorted Lists

![Difficulty](https://img.shields.io/badge/Difficulty-Easy-brightgreen) ![Platform](https://img.shields.io/badge/Platform-LeetCode-blue) ![Sorting](https://img.shields.io/badge/-Sorting-blueviolet) ![Linked List](https://img.shields.io/badge/-Linked%20List-blueviolet) 

## Problem Statement

You are given the heads of two sorted linked lists list1 and list2.

Merge the two lists into one sorted list. The list should be made by splicing together the nodes of the first two lists.

Return the head of the merged linked list.

## Examples

**Example 1:**

```
Input:  ([1,1,2,3,4,4], [1,2,3,4,4])
Output: [1,1,2,3,4,4]
```

**

**Example 2:**

```
Input:  (null, null)
Output: null
```

**

**Example 3:**

```
Input:  (null, [0])
Output: [0]
```

**

## Constraints

- `The number of nodes in both lists is in the range [0, 50].`
- `-100 <= Node.val <= 100`
- `Both list1 and list2 are sorted in non-decreasing order.`

## Solution

```kotlin
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
```


## Test Execution Results

| # | Input | Expected | Actual | Status | Time (ms) |
|---|-------|----------|--------|--------|-----------|
| 1 | `([1,1,2,3,4,4], [1,2,3,4,4])` | `[1,1,2,3,4,4]` | `[1,1,2,3,4,4]` | ✅ PASS | 0.026 |
| 2 | `(null, null)` | `null` | `null` | ✅ PASS | 0.001 |
| 3 | `(null, [0])` | `[0]` | `[0]` | ✅ PASS | 0.001 |

**3/3 tests passed** ✅

---

*Generated on 2026-05-23*
*Source: [https://leetcode.com/problems/merge-two-sorted-lists](https://leetcode.com/problems/merge-two-sorted-lists)*
