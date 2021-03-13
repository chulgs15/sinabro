package org.example.algorithm;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class PrintPriority {
  public static class Solution {
    public int solution(int[] priorities, int location) {
      int answer = 1;

      AtomicInteger integer = new AtomicInteger();
      LinkedList<print> collect = Arrays.stream(priorities)
          .mapToObj(x -> {
            return new print(integer.getAndIncrement(), x);
          })
          .collect(Collectors.toCollection(LinkedList::new));

      Comparator<print> comparator = new Comparator<print>() {
        @Override
        public int compare(print o1, print o2) {
          return o2.value - o1.value ;
        }
      };

      collect.sort(comparator);

      System.out.println(collect);

      for (print print : collect) {
        if (location == print.index) {
          break;
        }
        answer++;
      }

      return answer;
    }

    class print  {
      int index;
      int value;

      public print(int index, int value) {
        this.index = index;
        this.value = value;
      }

      @Override
      public String toString() {
        return "print{" +
            "index=" + index +
            ", value=" + value +
            '}' + "\n";
      }
    }
  }
}
