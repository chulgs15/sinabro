package org.example.algorithm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CycleRotationTest {

  CycleRotation.solution solution = new CycleRotation.solution();

  @Test
  public void example1() {
    Assertions.assertArrayEquals(solution.solution(new int[]{3, 8, 9, 7, 6}, 3), new int[]{9, 7, 6, 3, 8});
  }

  @Test
  public void example2() {
    int[] test = new int[]{0, 0, 0};
    Assertions.assertArrayEquals(solution.solution(test, 1), test);
  }

  @Test
  public void example3() {
    int[] test = new int[]{1, 2, 3, 4};
    Assertions.assertArrayEquals(solution.solution(test, 4), test);
  }

  @Test
  public void test1() {
    Assertions.assertArrayEquals(solution.solution(new int[]{3, 8, 9, 7, 6}, 6),
        new int[]{6, 3, 8, 9, 7});
  }
}
