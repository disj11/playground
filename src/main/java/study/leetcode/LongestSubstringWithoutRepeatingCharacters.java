package study.leetcode;

import java.util.HashSet;
import java.util.Set;

public class LongestSubstringWithoutRepeatingCharacters {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.lengthOfLongestSubstring("pwwkew"));
    }

    static class Solution {
        public int lengthOfLongestSubstring(String s) {
            int strLen = s.length();
            int left = 0;
            int right = 0;

            int maxLen = 0;
            Set<Character> characterSet = new HashSet<>();

            while (right < strLen) {
                char currentChar = s.charAt(right);
                if (!characterSet.contains(currentChar)) {
                    right++;
                    characterSet.add(currentChar);
                    maxLen = Math.max(maxLen, right - left);
                } else {
                    characterSet.remove(s.charAt(left++));
                }
            }

            return maxLen;
        }
    }
}
