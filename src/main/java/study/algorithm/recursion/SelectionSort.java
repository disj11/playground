package study.algorithm.recursion;

import java.util.Arrays;

public class SelectionSort {
    public static void main(String[] args) {
        int[] arr = {3, 2, 5, 1, 4};
        System.out.println(Arrays.toString(arr));
        sort(arr, 0, arr.length);
        System.out.println(Arrays.toString(arr));
    }

    private static int getMinIndex(int[] arr, int start, int end) {
        int sml = Integer.MAX_VALUE;
        int minIndex = 0;

        for (int i = start; i < end; i++) {
            if (sml > arr[i]) {
                sml = arr[i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    private static void sort(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }

        int minIndex = getMinIndex(arr, start, end);
        int temp = arr[start];
        arr[start] = arr[minIndex];
        arr[minIndex] = temp;

        sort(arr, start + 1, end);
    }
}
