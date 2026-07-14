package src.entity;

import src.entity.type.DrinkSize;
import src.entity.type.MilkType;
import src.entity.type.ToppingType;

import java.util.ArrayList;

public class CustomDrink implements Product {
    private CoreDrink baseDrink;
    private DrinkSize size;
    private MilkType milkType;
    private ArrayList<ToppingType> toppings;

    public CustomDrink() {
    }

    public CoreDrink getBaseDrink() {
        return baseDrink;
    }

    public boolean setBaseDrink(CoreDrink baseDrink) {
        if(baseDrink == null) {
            return false;
        }
        this.baseDrink = baseDrink;
        return true;
    }

    public DrinkSize getSize() {
        return size;
    }

    public boolean setSize(DrinkSize size) {
        if(size == null) {
            return false;
        }
        this.size = size;
        return true;
    }

    public MilkType getMilkType() {
        return milkType;
    }

    public boolean setMilkType(MilkType milkType) {
        if(milkType == null) {
            return false;
        }
        this.milkType = milkType;
        return true;
    }

    public ArrayList<ToppingType> getToppings() {
        return toppings;
    }

    public boolean setToppings(ArrayList<ToppingType> toppings) {
        if(toppings == null) {
            return false;
        }
        this.toppings = toppings;
        return true;
    }

    @Override
    public double getPrice() {
        double price = 0.0f;

        price += getBaseDrink().getPrice();
        price += getSize().getPrice();
        price += getMilkType().getPrice();

        if (getToppings() != null) {
            for (ToppingType topping : getToppings()) {
                price += topping.getPrice();
            }
        }

        return price;
    }

    @Override
    public String getName() {
        return getBaseDrink().getName() + " " + getSize().getName() + " (" + getMilkType().getName() + (getToppings() != null ? ", " + getToppings().size() + " topping)" : ")");
    }
}
