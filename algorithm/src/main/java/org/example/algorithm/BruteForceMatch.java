package org.example.algorithm;

public class BruteForceMatch {

  public static int bfMatch(String text, String pattern) {
    int result = -1;

    char[] texts = text.toCharArray();
    char[] patterns = pattern.toCharArray();

    if (patterns.length - texts.length > 0) {
      return result;
    }

    int mainLoppSize = texts.length - patterns.length + 1;
    mainLoop:
    for (int i = 0; i < mainLoppSize; i++) {
      if (texts[i] == patterns[0]) {
        int patternSize = patterns.length;
        for (int j = 1; j < patternSize; j++) {
          if (texts[i + j] != patterns[j]) {
            continue mainLoop;
          }
        }

        result = i;
        break;
      }
    }

    return result;
  }

  public static int bfMatchLast(String text, String pattern) {
    int result = -1;

    char[] texts = text.toCharArray();
    char[] patterns = pattern.toCharArray();

    int textSize = texts.length;
    int patternSize = patterns.length;

    if (patternSize - textSize > 0) {
      return result;
    }

    mainLoop:
    for (int i = textSize - 1; i > patternSize; i--) {
      if (texts[i] == patterns[patternSize - 1]) {
        for (int j = patternSize - 2; j != -1; j--) {
          if (texts[i + j - (patternSize - 1)] != patterns[j]) {
            continue mainLoop;
          }
        }

        result = i - patternSize + 1;
        break;
      }
    }

    return result;
  }
}
