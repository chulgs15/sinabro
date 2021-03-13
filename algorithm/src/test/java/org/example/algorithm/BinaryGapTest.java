package org.example.algorithm;

import org.example.algorithm.BinaryGap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class BinaryGapTest {

    BinaryGap.Solution solution = new BinaryGap.Solution();

    @Test
    public void exampleTest() {
        Assertions.assertEquals(solution.solution(9), 2);
        Assertions.assertEquals(solution.solution(529), 4);
        Assertions.assertEquals(solution.solution(20), 1);
        Assertions.assertEquals(solution.solution(15), 0);
        Assertions.assertEquals(solution.solution(32), 0);
    }
}
