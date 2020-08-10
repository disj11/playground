package study.pattern.decorator;

public class Whip implements CondimentDecorator {
    private final Beverage beverage;

    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Whip";
    }

    @Override
    public int getCost() {
        return beverage.getCost() + 500;
    }
}
