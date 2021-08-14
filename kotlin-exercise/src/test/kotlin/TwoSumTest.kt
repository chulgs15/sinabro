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

        assert(twoSum contentEquals intArrayOf(0))
    }

    @Test
    fun 배열길이와_2라면_결과는_동일하다() {
        val nums: IntArray = intArrayOf(3, 3)
        val target = 6

        val solution = Solution()
        val twoSum = solution.twoSum(nums, target)

        assert(twoSum contentEquals intArrayOf(0, 1))
    }

    @Test
    fun case_1() {
        val nums: IntArray = intArrayOf(2, 7, 11, 15)
        val target = 9

        val solution = Solution()
        val twoSum = solution.twoSum(nums, target)

        assert(twoSum contentEquals intArrayOf(0, 1))
    }

    @Test
    fun case_2() {
        val nums: IntArray = intArrayOf(3, 2, 4)
        val target = 6

        val solution = Solution()
        val twoSum = solution.twoSum(nums, target)

        assert(twoSum contentEquals intArrayOf(1, 2))
    }

    @Test
    fun case_3() {
        val nums: IntArray = intArrayOf(3, 3)
        val target = 6

        val solution = Solution()
        val twoSum = solution.twoSum(nums, target)

        assert(twoSum contentEquals intArrayOf(0, 1))
    }
//    [3,2,95,4,-3]
//    92

    @org.junit.Test
    fun case_4() {
        val nums: IntArray = intArrayOf(3,2,95,4,-3)
        val target = 92

        val solution = Solution()
        val twoSum = solution.twoSum(nums, target)

        assert(twoSum contentEquals intArrayOf(2, 4))

    }

}