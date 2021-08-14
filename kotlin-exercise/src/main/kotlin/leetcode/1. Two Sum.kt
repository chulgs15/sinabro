package leetcode

class Solution {
    fun twoSum(nums: IntArray, target: Int): IntArray {
        val length: Int = nums.size

        if (length == 1) {
            return intArrayOf(0)
        }

        if (length == 2) {
            return intArrayOf(0, 1)
        }

        val map: Map<Int, Int> = (1..length - 1)
            .associateBy(keySelector = { nums[it] }, valueTransform = { it })

        for (i in 0..length) {
            val idx = map.get(target - nums[i])
            if (idx != null && idx != i) {
                return intArrayOf(i, idx)
            }
        }

        return intArrayOf(0)
    }
}