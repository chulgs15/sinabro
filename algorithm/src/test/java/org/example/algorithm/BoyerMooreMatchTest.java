package org.example.algorithm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoyerMooreMatchTest {

  @Test
  public void example1() {
    String text = "DCABC";
    String pattern = "ABC";
    Assertions.assertEquals(BoyerMooreMatch.match(text, pattern), 2);
  }

  @Test
  public void example1_5() {
    String text = "ABCXDECABACABAC";
    String pattern = "ABAC";
    Assertions.assertEquals(BoyerMooreMatch.match(text, pattern), 7);
  }


  @Test
  public void example2() {
    String text = "ABC이지스DEF";
    String pattern = "이지스";
    Assertions.assertEquals(BoyerMooreMatch.match(text, pattern), 3);
  }

  @Test
  public void example3() {
    String text = "ABC이지스DEF이지스";
    String pattern = "이지스";
    Assertions.assertEquals(BoyerMooreMatch.match(text, pattern), 3);
  }

  @Test
  public void example4() {
    String text = "ABC이지스DEF이지스";
    String pattern = "사라다";
    Assertions.assertEquals(BoyerMooreMatch.match(text, pattern), -1);
  }

  @Test
  public void example5() {
    String text = "AB";
    String pattern = "이지스";
    Assertions.assertEquals(BoyerMooreMatch.match(text, pattern), -1);
  }

  @Test
  public void example6() {
    String text = "DAABC";
    String pattern = "ABC";
    Assertions.assertEquals(BoyerMooreMatch.matchLast(text, pattern), 2);
  }

  @Test
  public void example6_5() {
    String text = "ABCXDECABACABAC";
    String pattern = "ABAC";
    Assertions.assertEquals(BoyerMooreMatch.matchLast(text, pattern), 11);
  }


  @Test
  public void example7() {
    String text = "ABC이지스DEF";
    String pattern = "이지스";
    Assertions.assertEquals(BoyerMooreMatch.matchLast(text, pattern), 3);
  }

  @Test
  public void example8() {
    String text = "ABC이지스DEF이지스";
    String pattern = "이지스";
    Assertions.assertEquals(BoyerMooreMatch.matchLast(text, pattern), 9);
  }

  @Test
  public void example9() {
    String text = "ABC이지스DEF이지스";
    String pattern = "사라다";
    Assertions.assertEquals(BoyerMooreMatch.matchLast(text, pattern), -1);
  }

  @Test
  public void example10() {
    String text = "AB";
    String pattern = "이지스";
    Assertions.assertEquals(BoyerMooreMatch.matchLast(text, pattern), -1);
  }

}
