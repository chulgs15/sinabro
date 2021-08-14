import leetcode.IntArrayLeetCode
import org.junit.Test

class IntArrayTest {

    @Test
    fun array_길이와_m_같을경우() {
        val solution = IntArrayLeetCode.Solution()
        val result = solution.splitArray(intArrayOf(1, 2, 3), 3)
        println("result = $result")
        assert(result == 3)
    }

    @Test
    fun m_값이_1_경우_전체배열을_return() {
        val solution = IntArrayLeetCode.Solution()
        val result = solution.splitArray(intArrayOf(1, 2, 3), 1)
        assert(result == 6)
    }

    @Test
    fun case_1() {
        val solution = IntArrayLeetCode.Solution()
        val result = solution.splitArray(intArrayOf(7, 2, 5, 10, 8), 2)
        assert(result == 18)
    }

    @Test
    fun case_2() {
        val solution = IntArrayLeetCode.Solution()
        val result = solution.splitArray(intArrayOf(1,2,3,4,5), 2)
        assert(result == 9)
    }

    @Test
    fun case_3() {
        val solution = IntArrayLeetCode.Solution()
        val result = solution.splitArray(intArrayOf(1,4,4), 3)
        assert(result == 4)
    }

    @Test
    fun case_4() {
        val solution = IntArrayLeetCode.Solution()
        val result = solution.splitArray(intArrayOf(2,3,1,2,4,3), 5)
        assert(result == 4)
    }
}