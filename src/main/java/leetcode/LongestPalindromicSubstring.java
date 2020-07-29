package leetcode;

public class LongestPalindromicSubstring {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.longestPalindrome("ababbaba"));
    }

    static class Solution {
        private int index;
        private int maxLength;

        public String longestPalindrome(String s) {
            for (int i = 0; i < s.length(); i++) {
                extendPalindrome(s, i, i);
                extendPalindrome(s, i, i + 1);
            }

            return s.substring(index, index + maxLength);
        }

        private void extendPalindrome(String s, int left, int right) {
            while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
                left--;
                right++;
            }

            if (maxLength < right - left - 1) {
                maxLength = right - left - 1;
                index = left + 1;
            }
        }
    }
}
