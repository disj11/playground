package study.pattern.decorator;

public class Milk implements CondimentDecorator {
    private final Beverage beverage;

    public Milk(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Milk";
    }

    @Override
    public int getCost() {
        return beverage.getCost() + 500;
    }
}
