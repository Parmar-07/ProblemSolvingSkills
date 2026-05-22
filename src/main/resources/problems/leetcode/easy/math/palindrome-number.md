# 9. Palindrome Number

![Difficulty](https://img.shields.io/badge/Difficulty-Easy-brightgreen) ![Platform](https://img.shields.io/badge/Platform-LeetCode-blue) ![Math](https://img.shields.io/badge/-Math-blueviolet) ![Number Theory](https://img.shields.io/badge/-Number%20Theory-blueviolet) 

## Problem Statement

Given an integer x, return true if x is a palindrome, and false otherwise.

### Input / Output
- **Input:** A single integer x.
- **Output:** true if x is a palindrome, false otherwise.

## Examples

**Example 1:**

```
Input:  121
Output: true
```

*121 reads as 121 from left to right and from right to left.*

**Example 2:**

```
Input:  -121
Output: false
```

*From left to right it reads -121. From right to left it becomes 121-. Therefore it is not a palindrome.*

**Example 3:**

```
Input:  10
Output: false
```

*Reads 01 from right to left. Therefore it is not a palindrome.*

**Example 4:**

```
Input:  0
Output: true
```

*0 is its own reverse, so it is a palindrome.*

## Constraints

- `-2^31 <= x <= 2^31 - 1`

## Solution

```kotlin
override fun solve(input: Int): Boolean {

    // Negative numbers and non-zero multiples of 10
    // can never be palindromes
    if (input < 0 || (input != 0 && input % 10 == 0)) {
        return false
    }

    var original = input
    var reversed = 0

    // Reverse only half of the number
    while (original > reversed) {
        reversed = reversed * 10 + (original % 10)
        original /= 10
    }

    // Even length:
    // 1221 -> original=12, reversed=12
    //
    // Odd length:
    // 12321 -> original=12, reversed=123
    // Remove middle digit using /10
    return original == reversed ||
            original == reversed / 10
}
```


## Test Execution Results

| # | Input | Expected | Actual | Status | Time (ms) |
|---|-------|----------|--------|--------|-----------|
| 1 | `121` | `true` | `true` | ✅ PASS | 1.093 |
| 2 | `-121` | `false` | `false` | ✅ PASS | 0.002 |
| 3 | `10` | `false` | `false` | ✅ PASS | 0.001 |
| 4 | `0` | `true` | `true` | ✅ PASS | 0.001 |

**4/4 tests passed** ✅

---

*Generated on 2026-05-23*
*Source: [https://leetcode.com/problems/palindrome-number/](https://leetcode.com/problems/palindrome-number/)*
