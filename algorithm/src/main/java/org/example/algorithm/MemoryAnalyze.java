package org.example.algorithm;

public class MemoryAnalyze {
  public void stringTest() {
    String test = "";
    for (int i = 0; i < 100000; i++) {
      test += "" + i;
    }
  }

  public void stringBuilderTest() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < 100000; i++) {
      builder.append(i);
    }
  }
}
