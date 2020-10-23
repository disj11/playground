package study.programmers;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * @see <a href="https://programmers.co.kr/learn/courses/30/lessons/42626">더 맵게</a>
 */
public class MoreSpicy {
    public int solution(int[] scoville, int K) {
        int count = 0;
        PriorityQueue<Integer> queue = Arrays.stream(scoville)
                .boxed()
                .collect(Collectors.toCollection(PriorityQueue::new));

        while (queue.size() > 1 && queue.peek() < K) {
            int first = queue.poll();
            int second = queue.poll();

            queue.add(first + (second * 2));
            count++;
        }

        return queue.peek() > K ? count : -1;
    }

    public static void main(String[] args) {
        System.out.println(new MoreSpicy().solution(new int[]{1, 2, 3, 9, 10, 12}, 7));
    }
}
