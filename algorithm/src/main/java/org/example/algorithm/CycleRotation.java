package org.example.algorithm;

import java.util.Arrays;

public class CycleRotation {

  public static void main(String[] args) {
    System.out.println(10%5);
  }

  public static class solution {
    public int[] solution(int[] A, int K) {
      // 인자값과 반복이 같으면 동일 결과 return
      if (A.length == 0 || K == 0) {
        return A;
      }

      if ( A.length / K == K) {
        return A;
      }

      // 값이 모드 같은 경우 반복해도 의미가 없다.
      int redunduentCheck = (int) Arrays.stream(A)
          .distinct()
          .count();

      if (redunduentCheck == 1) {
        return A;
      }

      int[] result = mapTo(A, K);
      return result;
    }

    private int[] mapTo(int[] a, int k) {
      int length = a.length;
      int[] result = new int[a.length];

      for (int i = 0; i < length; i++) {
        int index = i + k >= length ? (i + k) % length : i + k;
        result[index] = a[i];
      }

      return result;
    }
  }
}
