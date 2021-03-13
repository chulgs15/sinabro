package org.example.algorithm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FrogJmpTest {

  FrogJmp.Solution solution = new FrogJmp.Solution();


  @Test
  public void example1() {
    Assertions.assertEquals(solution.solution(10, 85, 30),3 );
  }

  @Test
  public void example2() {
    Assertions.assertEquals(solution.solution(10, 10, 30),0 );
  }

}
