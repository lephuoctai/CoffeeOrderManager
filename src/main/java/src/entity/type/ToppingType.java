package src.entity.type;

import src.entity.Product;

public enum ToppingType implements Product {
    ESPRESSO_SHOT(10000f, "Espresso Shot"),
    VANILLA_SYRUP(5000f, "Syrup Vani"),
    WHIPPED_CREAM(7000f, "Kem tươi");


    private final String name;
    private final double price;

    ToppingType(double price, String name) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
