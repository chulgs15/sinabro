package org.example.algorithm;

public class FrogJmp {
  public static class Solution {
    public int solution(int X, int Y, int D) {
      if (X == Y) {
        return 0;
      }

      double x = X;
      double y = Y;
      double d = D;

      return (int) Math.ceil((y - x) / d);
    }
  }

}
