package leetcode

class Solution {
    fun twoSum(nums: IntArray, target: Int): IntArray {
        val length: Int = nums.size

        if (length == 1) {
            return intArrayOf(0)
        }

        if (length == 2) {
            return intArrayOf(0,1)
        }

        var result = 0
        for (i in 0..length - 2) {
            var tmpValue = nums[i]
            for (j in i..length - 1) {
                tmpValue += nums[j]
                if (tmpValue > target) {
                    break;
                }
            }
        }

        return intArrayOf(1,2,3)
    }
}