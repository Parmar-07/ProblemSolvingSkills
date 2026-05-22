# 1. Two Sum

![Difficulty](https://img.shields.io/badge/Difficulty-Easy-brightgreen) ![Platform](https://img.shields.io/badge/Platform-LeetCode-blue) ![Array](https://img.shields.io/badge/-Array-blueviolet) ![Math](https://img.shields.io/badge/-Math-blueviolet) ![HashMap](https://img.shields.io/badge/-HashMap-blueviolet) 

## Problem Statement

Given an array of integers array and an integer target, return indices of the two numbers such that they add up to target.

You may assume that each input would have exactly one solution, and you may not use the same element twice.

You can return the answer in any order.

## Examples

**Example 1:**

```
Input:  [2,7,11,5] target = 9
Output: [0, 1]
```

*Because array[0] + array[1] == 9, we return [0, 1].*

**Example 2:**

```
Input:  [3,2,4] target = 6
Output: [1, 2]
```

*Because array[1] + array[2] == 6, we return [1, 2].*

**Example 3:**

```
Input:  [3,3] target = 6
Output: [0, 1]
```

*Because array[0] + array[1] == 6, we return [0, 1].*

## Constraints

- `2 <= array.length <= 10^4`
- `-10^9 <= array[i] <= 10^9`
- `-10^9 <= target <= 10^9`

## Solution

```kotlin
override fun solve(input: TwoSum): IntArray {

    // HashMap:
    // number -> index
    val mapIndexValue = hashMapOf<Int,Int>()

    // inputs (array, target)
    val array = input.array
    val target = input.target

    // Loop through array indexes
    for (index in array.indices) {

        // Current number from index
        val current = array[index]

        // Number needed to reach target
        val need =  target - current

        // Check if needed number already exists in stored map value
        if (mapIndexValue.containsKey(need)){
            // return indexes
            return intArrayOf(mapIndexValue[need]!!,index)
        }

        // Store current number and index in map
        mapIndexValue[current] = index
    }

    // If not found target return empty array
    return intArrayOf()
}       
```


## Test Execution Results

| # | Input | Expected | Actual | Status | Time (ms) |
|---|-------|----------|--------|--------|-----------|
| 1 | `[2,7,11,5] target = 9` | `[0, 1]` | `[0, 1]` | ✅ PASS | 0.114 |
| 2 | `[3,2,4] target = 6` | `[1, 2]` | `[1, 2]` | ✅ PASS | 0.004 |
| 3 | `[3,3] target = 6` | `[0, 1]` | `[0, 1]` | ✅ PASS | 0.001 |

**3/3 tests passed** ✅

---

*Generated on 2026-05-23*
*Source: [https://leetcode.com/problems/two-sum/](https://leetcode.com/problems/two-sum/)*
