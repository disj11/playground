package study.algorithm.recursion;

public class BinaryString {
    public static void main(String[] args) {
        System.out.println(toBinaryString(255));
    }

    private static String toBinaryString(int decimal) {
        if (decimal / 2 < 1) {
            return String.valueOf(decimal % 2);
        }

        return toBinaryString(decimal / 2) + (decimal % 2);
    }
}
