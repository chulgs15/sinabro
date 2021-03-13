package org.example.algorithm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OddOccurenceInArray {


  OddOccurrencesInArray.Solution solution = new OddOccurrencesInArray.Solution();

  @Test
  public void example1() {
    Assertions.assertEquals(solution.solution(new int[]{9, 3, 9, 3, 9, 7, 9}),7 );
  }

  @Test
  public void example2() {
    Assertions.assertEquals(solution.solution(new int[]{9, 9, 9}),9 );
  }

  @Test
  public void example3() {
    Assertions.assertEquals(solution.solution(new int[]{9}),9 );
  }

  @Test
  public void example4() {
    Assertions.assertEquals(solution.solution(new int[]{9, 9, 9, 9, 9}),9 );
  }

  @Test
  public void performance_test() {
    

    Assertions.assertEquals(solution.solution(new int[]{9, 9, 9, 9, 9}),9 );
  }

}
