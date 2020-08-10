package study.pattern.decorator;

public class Mocha implements CondimentDecorator {
    private final Beverage beverage;

    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Mocha";
    }

    @Override
    public int getCost() {
        return beverage.getCost() + 500;
    }
}
