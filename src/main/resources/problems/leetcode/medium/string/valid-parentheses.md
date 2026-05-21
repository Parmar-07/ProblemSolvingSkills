# 20. Valid Parentheses

![Difficulty](https://img.shields.io/badge/Difficulty-Medium-yellow) ![Platform](https://img.shields.io/badge/Platform-LeetCode-blue) ![String](https://img.shields.io/badge/-String-blueviolet) ![Stack](https://img.shields.io/badge/-Stack-blueviolet) 

## Problem Statement

Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.

An input string is valid if:

Open brackets must be closed by the same type of brackets.
Open brackets must be closed in the correct order.
Every close bracket has a corresponding open bracket of the same type.

## Examples

**Example 1:**

```
Input:  ()
Output: true
```

**

**Example 2:**

```
Input:  ()[]{}
Output: true
```

**

**Example 3:**

```
Input:  (]
Output: false
```

**

**Example 4:**

```
Input:  ([])
Output: true
```

**

**Example 5:**

```
Input:  ([)]
Output: false
```

**

## Constraints

- `1 <= s.length <= 10^4`
- `s consists of parentheses only '()[]{}'.`

## Solution

```kotlin
override fun solve(input: String): Boolean {

    // Create a stack to keep track of opening brackets
    val stack = mutableListOf<Char>()
    // Map each opening bracket to its corresponding closing bracket
    val map = mapOf('(' to ')', '{' to '}', '[' to ']')

    // Loop through each character in the string
    for (char in input) {
        if (char in map.keys) {
            // If it's an opening bracket, push it onto the stack
            stack.add(char)
        } else if (stack.isNotEmpty() && map[stack.last()] == char) {
            // If it's a closing bracket and it matches the top of the stack, pop the top of the stack
            stack.removeAt(stack.size - 1)
        } else {
            // If it's a mismatched closing or stack is empty, return false
            return false
        }
    }

    // After processing, if the stack is empty, all brackets were matched
    return stack.isEmpty()
}
```


## Test Execution Results

| # | Input | Expected | Actual | Status | Time (ms) |
|---|-------|----------|--------|--------|-----------|
| 1 | `()` | `true` | `true` | ✅ PASS | 2.055 |
| 2 | `()[]{}` | `true` | `true` | ✅ PASS | 0.042 |
| 3 | `(]` | `false` | `false` | ✅ PASS | 0.008 |
| 4 | `([])` | `true` | `true` | ✅ PASS | 0.008 |
| 5 | `([)]` | `false` | `false` | ✅ PASS | 0.007 |

**5/5 tests passed** ✅

---

*Generated on 2026-05-21*
*Source: [https://leetcode.com/problems/valid-parentheses/](https://leetcode.com/problems/valid-parentheses/)*
