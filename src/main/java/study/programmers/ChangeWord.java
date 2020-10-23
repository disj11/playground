package study.programmers;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class ChangeWord {
    public static int solution(String begin, String target, String[] words) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(begin);

        int depth = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                String currentWord = queue.poll();
                if (visited.contains(currentWord)) {
                    continue;
                }
                visited.add(currentWord);

                if (currentWord.equals(target)) {
                    return depth;
                }

                for (String nextWord : words) {
                    if (canChange(currentWord, nextWord)) {
                        queue.add(nextWord);
                    }
                }
            }

            depth++;
        }

        return 0;
    }

    private static boolean canChange(String str1, String str2) {
        char[] charArr1 = str1.toCharArray();
        char[] charArr2 = str2.toCharArray();
        int count = 0;

        for (int i = 0; i < charArr1.length && count < 2; i++) {
            if (charArr1[i] != charArr2[i]) {
                count++;
            }
        }

        return count < 2;
    }

    public static void main(String[] args) {
        System.out.println(solution("hit", "cog", new String[]{"hot", "dot", "dog", "lot", "log", "cog"}));
    }
}
