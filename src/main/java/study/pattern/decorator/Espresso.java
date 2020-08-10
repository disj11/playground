package study.pattern.decorator;

public class Espresso implements Beverage {
    @Override
    public String getDescription() {
        return "Espresso";
    }

    @Override
    public int getCost() {
        return 3000;
    }
}
