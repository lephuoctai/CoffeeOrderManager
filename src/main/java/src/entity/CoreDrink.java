package src.entity;

public abstract class CoreDrink implements Product {
     private final double price;
     private final String name;

    public CoreDrink(double price, String name) {
        this.price = price;
        this.name = name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name;
    }
}
