import leetcode.IntArrayLeetCode
import leetcode.Solution
import kotlin.test.Test

class TwoSumTest {
    @Test
    fun 배열길이가_1이면_결과는_동일하다() {
        val nums: IntArray = intArrayOf(3)
        val target = 3

        val solution = Solution()
        val twoSum = solution.twoSum(nums, target)

        assert(twoSum == intArrayOf(0))
    }

    @Test
    fun 배열길이와_2라면_결과는_동일하다() {
        val nums: IntArray = intArrayOf(3, 3)
        val target = 6

        val solution = Solution()
        val twoSum = solution.twoSum(nums, target)

        assert(twoSum == intArrayOf(0))
    }

    @Test
    fun case_1() {
        val nums: IntArray = intArrayOf(1, 2, 2, 4)
        val target = 3

        val solution = Solution()
        val twoSum = solution.twoSum(nums, target)

        assert(twoSum == intArrayOf(2,3))
    }
}