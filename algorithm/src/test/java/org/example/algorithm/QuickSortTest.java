package org.example.algorithm;

import org.example.algorithm.sort.quicksort.Quicksort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Random;

public class QuickSortTest {

  @Test
  public void performanceTest() {

    Quicksort quicksort = new Quicksort();

    int[] singleThreadTest = new Random().ints(100, 1, 100).toArray();
    int[] multiThreadTest = new int[singleThreadTest.length];
    System.arraycopy(singleThreadTest, 0, multiThreadTest, 0, singleThreadTest.length);

    LocalTime startTime = LocalTime.now();
    quicksort.quickSort(singleThreadTest);
    LocalTime endTime = LocalTime.now();

    Duration duration = Duration.between(startTime, endTime);
    System.out.println("Single Thread = " + duration.toNanos());

    startTime = LocalTime.now();
    Arrays.sort(multiThreadTest);
    endTime = LocalTime.now();

    duration = Duration.between(startTime, endTime);
    System.out.println("Multi Thread  = " + duration.toNanos());

    Assertions.assertArrayEquals(singleThreadTest, multiThreadTest);
  }
}
