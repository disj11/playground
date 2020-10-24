package study.programmers;

import java.util.Arrays;

/**
 * @see <a href="https://programmers.co.kr/learn/courses/30/lessons/42584">주식 가격</a>
 */
public class StockPrice {
    public int[] solution(int[] prices) {
        final int length = prices.length;
        int[] answer = new int[length];

        for (int i = 0; i < length - 1; i++) {
            int count = 1;
            for (int j = i + 1; j < length; j++) {
                if (prices[i] > prices[j] || j == length - 1) {
                    answer[i] = count;
                    break;
                }

                count++;
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new StockPrice().solution(new int[]{1, 2, 3, 2, 3})));
    }
}
