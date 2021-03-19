package org.example.algorithm;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class PrintPriority {
  public static class Solution {
    public int solution(int[] priorities, int location) {
      int answer = 0;

      int l = location;

      Queue<Integer> que = new LinkedList<Integer>();
      for(int i : priorities){
        que.add(i);
      }

      Arrays.sort(priorities);
      int size = priorities.length-1;



      while(!que.isEmpty()){
        Integer i = que.poll();
        if(i == priorities[size - answer]){
          answer++;
          l--;
          if(l <0)
            break;
        }else{
          que.add(i);
          l--;
          if(l<0)
            l=que.size()-1;
        }
      }

      return answer;
    }

    public int solution2(int[] priorities, int location) {
      int answer = 1;

      // 최대값을 찾아 index와 값을 가져온다.
      int maxIndex = 0;
      int maxValue = -1;
      for (int i = 0; i < priorities.length; i++) {
        if (priorities[i] > maxValue) {
          maxIndex = i;
          maxValue = priorities[i];
        }
      }

      for (int i = maxIndex; i < priorities.length; i++) {
        if (i == location) {
          return i - maxIndex + 1;
        }
      }

      for (int i = 0; i < maxIndex; i++) {
        if (i == location) {
          return i + priorities.length - maxIndex + 1;
        }
      }


      return answer;
    }
  }
}
