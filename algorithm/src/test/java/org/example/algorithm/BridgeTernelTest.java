package org.example.algorithm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BridgeTernelTest {

  BridgeTernel.Solution solution = new BridgeTernel.Solution();

  @Test
  public void example1() {
    int[] test = new int[]{7, 4, 5, 6};
    Assertions.assertEquals(this.solution.solution(2, 10, test), 8);
  }

  @Test
  public void example2() {
    int[] test = new int[]{10};
    Assertions.assertEquals(solution.solution(100, 100, test), 101);
  }

  @Test
  public void example3() {
    int[] test = new int[]{10, 10, 10, 10, 10, 10, 10, 10, 10, 10,};
    Assertions.assertEquals(solution.solution(100, 100, test), 110);
  }
}
