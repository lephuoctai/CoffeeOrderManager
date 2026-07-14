package src.entity.type;

import src.entity.Product;

public enum MilkType implements Product {
    Regular(0f, "Sữa Thường"),
    SOY(6000f, "Sữa Đậu Nành"),
    OAT(8000f, "Sữa Yến Mạch");

    private final String name;
    private final double price;

    MilkType( double price, String displayName) {
        this.name = displayName;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
