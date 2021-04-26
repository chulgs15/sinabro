package org.example.algorithm;

import java.util.Set;
import java.util.stream.Collectors;

public class BoyerMooreMatch {

    public static int match(String text, String pattern) {
        Set<Character> set = pattern.chars().mapToObj(x -> (char) x).collect(Collectors.toSet());

        for (int i = 0; i <= text.length() - pattern.length(); ) {
            int j = pattern.length() - 1;

            while (text.charAt(j + i) == pattern.charAt(j) && j != 0) {
                j--;
            }

            if (j == 0) {
                return i;
            } else {
                if (set.contains(text.charAt(j + i))) {
                    i++;
                } else {
                    i += pattern.length();
                }
            }
        }

        return -1;
    }


    public static int matchLast(String text, String pattern) {
        Set<Character> set = pattern.chars().mapToObj(x -> (char) x).collect(Collectors.toSet());

        if (pattern.length() > text.length()) {
            return -1;
        }

        for (int i = text.length() - pattern.length(); i != 0; ) {
            int j = pattern.length() - 1;

            while (text.charAt(i + j) == pattern.charAt(j) && j != 0) {
                j--;
            }

            if (j == 0) {
                return i;
            } else {
                if (set.contains(text.charAt(i + j))) {
                    i--;
                } else {
                    i -= pattern.length();
                }
            }
        }

        return -1;
    }

}
