# 2553. Separate the Digits in an Array

![Difficulty](https://img.shields.io/badge/Difficulty-Easy-brightgreen) ![Platform](https://img.shields.io/badge/Platform-LeetCode-blue) ![Array](https://img.shields.io/badge/-Array-blueviolet) ![Math](https://img.shields.io/badge/-Math-blueviolet) 

## Problem Statement

Given an array of positive integers nums, return an array answer that consists
of the digits of each integer in nums after separating them in the same order
they appear in nums.

To separate the digits of an integer is to get all the digits it has in the
same order. For example, for the integer 10921, the separation of its digits
is [1, 0, 9, 2, 1].

### Input / Output
- **Input:** An array of positive integers.
- **Output:** An array of individual digits in the original order.

## Examples

**Example 1:**

```
Input:  [13, 25, 83, 77]
Output: [1, 3, 2, 5, 8, 3, 7, 7]
```

*The separations of 13, 25, 83, 77 are [1,3], [2,5], [8,3], [7,7]. Concatenated in order: [1,3,2,5,8,3,7,7].*

**Example 2:**

```
Input:  [7, 1, 3, 9]
Output: [7, 1, 3, 9]
```

*Each single-digit number separates to itself: [7,1,3,9].*

## Constraints

- `1 <= nums.length <= 1000`
- `1 <= nums[i] <= 10^5`

## Solution

```kotlin
 override fun solve(input: IntArray): IntArray {
   val result = mutableListOf<Int>()

   for (num in input) {
     var n = num
     val digits = mutableListOf<Int>()

     // Extract digits from least-significant to most-significant
     while (n > 0) {
         digits.add(n % 10)  // e.g. 13 % 10 = 3
         n /= 10             // e.g. 13 / 10 = 1
     }

     // Reverse to restore original digit order, then append
     digits.reversed().forEach { result.add(it) }
   }

 return result.toIntArray()
}
```


## Test Execution Results

| # | Input | Expected | Actual | Status | Time (ms) |
|---|-------|----------|--------|--------|-----------|
| 1 | `[13, 25, 83, 77]` | `[1, 3, 2, 5, 8, 3, 7, 7]` | `[1, 3, 2, 5, 8, 3, 7, 7]` | ✅ PASS | 0.061 |
| 2 | `[7, 1, 3, 9]` | `[7, 1, 3, 9]` | `[7, 1, 3, 9]` | ✅ PASS | 0.011 |

**2/2 tests passed** ✅

---

*Generated on 2026-05-23*
*Source: [https://leetcode.com/problems/separate-the-digits-in-an-array/](https://leetcode.com/problems/separate-the-digits-in-an-array/)*
