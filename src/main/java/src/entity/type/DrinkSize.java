package src.entity.type;

import src.entity.Product;

public enum DrinkSize implements Product {
    SMALL(0f, "Nhỏ"),
    MEDIUM(5000f, "Vừa"),
    LARGE(10000f, "Lớn");

    private final double price;
    private final String name;

    DrinkSize(double price, String name) {
        this.price = price;
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
