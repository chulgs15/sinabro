package org.example.algorithm;

import java.util.ArrayList;
import java.util.List;

public class BinaryGap {

  public static class Solution {
    public int solution(int N) {
      // 최소값과 최대값처리.
      if (N == 0 || N == 1 || Integer.MAX_VALUE == N) {
        return 0;
      }

      // 2진 트리로 진행해야 한다.
      List<Integer> list = mapTo(N);
      return getMax(list);
    }

    public List<Integer> mapTo(int N) {
      List<Integer> result = new ArrayList<>();

      int pr = 30;
      int pl = 0;
      int pc = 15;
      int median;

      while (N != 0) {
        // 이진 트리로 값의 범위를 줄여보자.
        while (pr - pl >= 5) {
          median = (int) Math.pow(2d, pc);
          if (median > N) {
            pr = pc;
            pc = (pr + pl) / 2;
          } else if (median < N) {
            pl = pc;
            pc = (pr + pl) / 2;
          } else {
            result.add(pc);
            return result;
          }
        }

        // 5개 이하는 Full Scan.
        for (; ; pl++) {
          median = (int) Math.pow(2d, pl);
          if (N - median < 0) {
            break;
          }
        }

        pl = pl - 1;
        result.add(pl);
        N -= (int) Math.pow(2d, pl);

        // 범위를 줄이면서 초기화.
        pr = pl;
        pl = 0;
        pc = (pr + pl) / 2;
      }

      return result;
    }

    public int getMax(List<Integer> list) {
      int size = list.size();
      int max = 0;

      if (size == 1) {
        return max;
      }

      for (int i = 0; i < size - 1; i++) {
        max = Math.max(list.get(i) - list.get(i + 1) - 1, max);
      }

      return max;
    }
  }

}
