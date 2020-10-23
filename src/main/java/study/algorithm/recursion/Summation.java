package study.algorithm.recursion;

public class Summation {
    public static void main(String[] args) {
        System.out.println(getSummation(10));
    }

    private static int getSummation(int value) {
        if (value == 1) {
            return value;
        }

        return getSummation(value - 1) + value;
    }
}
