package study.algorithm.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Combination {
    public static List<String[]> generate(List<String> list, int r) {
        List<String[]> results = new ArrayList<>();
        helper(list, results, new String[r], 0, list.size() - 1, 0);
        return results;
    }

    private static void helper(List<String> list, List<String[]> results, String[] data, int start, int end, int index) {
        if (data.length == index) {
            results.add(data.clone());
        } else if (start <= end) {
            data[index] = list.get(start);
            helper(list, results, data, start + 1, end, index + 1);
            helper(list, results, data, start + 1, end, index);
        }
    }

    public static void main(String[] args) {
        List<String[]> combinations = generate(Arrays.asList("A", "B", "C", "D"), 2);
        for (String[] combination : combinations) {
            System.out.println(Arrays.toString(combination));
        }
    }
}
