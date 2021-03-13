package org.example.algorithm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class OddOccurrencesInArray {

  public static class Solution {
    public int solution(int[] A){
      if (A.length == 1) {
        return A[0];
      }

      // 뭔가 이상하지만...이건 Hashmap 문제야...
      // 정렬과 검색을 하면 모든 라인을 2번 돌앙야해..
      // 그렇지 않을 려면 1번의 Scan으로 값의 빈도수를 알아야해.

      return mapTo(A);
    }

    private int mapTo(int[] A) {
      // Key 는 값
      // Value 는 빈도
      int result = 0;

      Map<Integer, Integer> map = new HashMap<>();
      for (int i : A) {
        map.put(i, map.getOrDefault(i, 0) + 1);
      }

      for (Integer integer : map.keySet()) {
        Integer value = map.get(integer);
        if (value  % 2 == 1){
          result = integer;
          break;
        }
      }

      return result;
    }


  }
}
