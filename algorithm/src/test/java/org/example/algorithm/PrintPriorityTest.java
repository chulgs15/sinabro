package org.example.algorithm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PrintPriorityTest {

  PrintPriority.Solution solution = new PrintPriority.Solution();

  @Test
  public void example1() {
    int[] test = new int[]{2, 1, 3, 2};
    Assertions.assertEquals(solution.solution(test, 2), 1);
  }

  @Test
  public void example2() {
    int[] test = new int[]{1, 1, 9, 1, 1, 1};
    Assertions.assertEquals(solution.solution(test, 0), 5);
  }

  @Test
  public void example3() {
    int[] test = new int[]{1, 2, 100};
    Assertions.assertEquals(solution.solution(test, 0), 2);
  }


  @Test
  public void example4() {
    int[] test = new int[]{1, 1, 1, 1};
    Assertions.assertEquals(solution.solution(test, 0), 0);
  }}
