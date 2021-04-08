package org.example.algorithm.sort.quicksort;

import java.util.Stack;

public class Quicksort {

  private static final int QUICKSORT_SORT_LIMIT = 10;

  synchronized static void swap(int[] a, int idx1, int idx2) {
    int t = a[idx1];
    a[idx1] = a[idx2];
    a[idx2] = t;
  }

  public void quickSort(int[] a) {
    Stack<Integer> leftIndexStack = new Stack<>();
    Stack<Integer> rightIndexStack = new Stack<>();

    leftIndexStack.push(0);
    rightIndexStack.push(a.length - 1);

    int startIndex,
        endIndex,
        leftPoint,
        rightPoint,
        pivot;

    while (!leftIndexStack.isEmpty()) {
      leftPoint = startIndex = leftIndexStack.pop(); // 왼쪽 커서
      rightPoint = endIndex = rightIndexStack.pop();  // 오른쪽 커서
      pivot = a[(startIndex + endIndex) / 2]; // 피벗

      if (rightPoint - leftPoint < QUICKSORT_SORT_LIMIT) {
        for (int i = leftPoint; i != rightPoint; i++) {
          for (int j = i; j != rightPoint + 1; j++) {
            if (a[i] > a[j]) {
              swap(a, i, j);
            }
          }
        }
      } else {
        do {
          while (a[leftPoint] < pivot) leftPoint++;
          while (pivot < a[rightPoint]) rightPoint--;

          if (leftPoint <= rightPoint) {
            swap(a, leftPoint++, rightPoint--);
          }
        } while (leftPoint <= rightPoint);

        if (startIndex < rightPoint) {
          leftIndexStack.push(startIndex);
          rightIndexStack.push(rightPoint);
        }
        if (leftPoint < endIndex) {
          leftIndexStack.push(leftPoint);
          rightIndexStack.push(endIndex);
        }
      }
    }
  }

}
