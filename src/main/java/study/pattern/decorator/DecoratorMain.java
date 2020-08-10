package study.pattern.decorator;

public class DecoratorMain {
    public static void main(String[] args) {
        Beverage espresso = new Espresso();
        System.out.println(espresso.getDescription() + " / " + espresso.getCost());

        Beverage latte = new Milk(espresso);
        System.out.println(latte.getDescription() + " / " + latte.getCost());

        Beverage mocha = new Mocha(latte);
        System.out.println(mocha.getDescription() + " / " + mocha.getCost());

        Beverage whippedMocha = new Whip(mocha);
        System.out.println(whippedMocha.getDescription() + " / " + whippedMocha.getCost());
    }
}
