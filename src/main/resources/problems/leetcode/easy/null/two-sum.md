# 1. Two Sum

![Difficulty](https://img.shields.io/badge/Difficulty-Easy-brightgreen) ![Platform](https://img.shields.io/badge/Platform-LeetCode-blue) 

## Problem Statement

Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.

You may assume that each input would have exactly one solution, and you may not use the same element twice.

You can return the answer in any order.

## Examples

**Example 1:**

```
Input:  [2,7,11,5] target = 9
Output: [0, 1]
```

*Because nums[0] + nums[1] == 9, we return [0, 1].*

**Example 2:**

```
Input:  [3,2,4] target = 6
Output: [1, 2]
```

*Because nums[1] + nums[2] == 6, we return [1, 2].*

**Example 3:**

```
Input:  [3,3] target = 6
Output: [0, 1]
```

*Because nums[0] + nums[1] == 6, we return [0, 1].*

## Solution

```kotlin
override fun solve(input: TwoSum): IntArray {
    val mapIndexValue = hashMapOf<Int,Int>()

    val array = input.array
    val target = input.target

    for (index in array.indices) {

        val current = array[index]
        val need =  target - current

        if (mapIndexValue.containsKey(need)){
            return intArrayOf(mapIndexValue[need]!!,index)
        }

        mapIndexValue[current] = index
    }

    return intArrayOf()
}        
```


## Test Execution Results

| # | Input | Expected | Actual | Status | Time (ms) |
|---|-------|----------|--------|--------|-----------|
| 1 | `[2,7,11,5] target = 9` | `[0, 1]` | `[0, 1]` | ✅ PASS | 0.894 |
| 2 | `[3,2,4] target = 6` | `[1, 2]` | `[1, 2]` | ✅ PASS | 0.007 |
| 3 | `[3,3] target = 6` | `[0, 1]` | `[0, 1]` | ✅ PASS | 0.002 |

**3/3 tests passed** ✅

---

*Generated on 2026-05-23*
*Source: [https://leetcode.com/problems/two-sum/](https://leetcode.com/problems/two-sum/)*
