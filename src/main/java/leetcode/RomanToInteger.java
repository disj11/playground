package leetcode;

import java.util.HashMap;
import java.util.Map;

public class RomanToInteger {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.romanToInt("III"));
    }

    static class Solution {
        private static Map<Character, Integer> roman = new HashMap<>();
        static {
            roman.put('M', 1000);
            roman.put('D', 500);
            roman.put('C', 100);
            roman.put('L', 50);
            roman.put('X', 10);
            roman.put('V', 5);
            roman.put('I', 1);
        }

        public int romanToInt(String s) {
            int result = 0;
            for (int i = 0; i < s.length(); i++) {
                int current = roman.get(s.charAt(i));
                int next = i + 1 < s.length() ? roman.get(s.charAt(i + 1)) : 0;

                if (current >= next) {
                    result += current;
                } else {
                    result -= current;
                }
            }

            return result;
        }
    }
}
