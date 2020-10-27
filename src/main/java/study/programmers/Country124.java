package study.programmers;

/**
 * @see <a href="https://programmers.co.kr/learn/courses/30/lessons/12899">124 나라의 숫자</a>
 */
public class Country124 {
    public String solution(int n) {
        int num = n;
        int remainder;
        StringBuilder answer = new StringBuilder();

        while (num > 0) {
            remainder = num % 3;
            num /= 3;

            if (num == 0) {
                answer.insert(0, remainder);
                break;
            } else {
                if (remainder == 0) {
                    answer.insert(0, "4");
                    num -= 1;
                } else {
                    answer.insert(0, remainder);
                }
            }
        }

        return answer.toString();
    }

    public static void main(String[] args) {
        Country124 country124 = new Country124();
        System.out.println(country124.solution(1));
        System.out.println(country124.solution(2));
        System.out.println(country124.solution(3));
        System.out.println(country124.solution(4));
        System.out.println(country124.solution(5));
        System.out.println(country124.solution(6));
        System.out.println(country124.solution(7));
    }
}
