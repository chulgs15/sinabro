package leetcode

class IntArrayLeetCode {
    class Solution {
        fun splitArray(nums: IntArray, m: Int): Int {
            // 최대 숫자를  return 한다.
            infix fun Int.max(a: Int): Int = if (this > a) this else a;

            if (nums.size == m) {
                var max:Int = 0;
                for (num in nums) {
                    max = max.max(num)
                }

                return max;
            }

            if (m == 1) {
                return nums.sum()
            }

            var maxValue = 0
            for (i in 0..nums.size - m) {
                val lastPoint:Int = i + m -1;
                var tempMaxValue:Int = 0;
                for (j in i..lastPoint) {
                    tempMaxValue += nums.get(j)
                }
                maxValue = maxValue.max(tempMaxValue)
            }

            return maxValue
        }
    }

}

fun main(args: Array<String>) {



}