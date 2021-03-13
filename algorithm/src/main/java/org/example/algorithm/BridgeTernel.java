package org.example.algorithm;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public class BridgeTernel {

  static class Solution {
    public int solution(int bridge_length, int weight, int[] truck_weights) {
      int answer = 0;

      if (truck_weights.length == 1) {
        return (int) Math.round((double) bridge_length / (double) truck_weights.length) + 1;
      }

      Queue<Integer> queue = new ConcurrentLinkedDeque<>();

      int sum = 0;
      for (int truck_weight : truck_weights) {
        while (true) {
          if (queue.isEmpty()) {
            queue.add(truck_weight);
            answer++;
            sum += truck_weight;
            break;
          } else if (queue.size() == bridge_length) {
            sum -= queue.poll();
          } else {
            if (sum + truck_weight > weight) {
              queue.add(0);
              answer++;
            } else {
              queue.add(truck_weight);
              answer++;
              sum += truck_weight;
              break;
            }
          }
        }
      }

      return answer + bridge_length;
    }
  }
}
