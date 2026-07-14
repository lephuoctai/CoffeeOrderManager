package src.controller;

import src.entity.*;
import src.entity.type.DrinkSize;
import src.entity.type.MilkType;
import src.entity.type.ToppingType;

import java.util.ArrayList;

/**
 * The DrinkOrder class is responsible for creating and managing a custom drink order.
 * It allows you to create a new drink, add core drinks, sizes, milk types, and toppings to the order.
 * It also provides a method to validate the drink order.
 */
public class DrinkOrder {
    private CustomDrink currentDrink;

    public DrinkOrder() {
    }

    public DrinkOrder createDrink() {
        this.currentDrink = new CustomDrink();
        return this;
    }

    public CustomDrink getCurrentDrink() {
        return currentDrink;
    }

    public DrinkOrder add(CoreDrink coreDrink) throws ClassNotFoundException {
        if(this.currentDrink == null) throw new ClassNotFoundException("Please create drink first");
        if(!this.currentDrink.setBaseDrink(coreDrink)) throw new IllegalArgumentException("Base drink is missing");

        return this;
    }

    public DrinkOrder add(DrinkSize size) throws ClassNotFoundException {
        if(this.currentDrink == null) throw new ClassNotFoundException("Please create drink first");
        if(!this.currentDrink.setSize(size)) throw new IllegalArgumentException("Size is missing");

        return this;
    }

    public DrinkOrder add(MilkType milkType) throws ClassNotFoundException {
        if(this.currentDrink == null) throw new ClassNotFoundException("Please create drink first");
        if(!this.currentDrink.setMilkType(milkType)) throw new IllegalArgumentException("Milk type is missing");

        return this;
    }

    public DrinkOrder add(ToppingType topping) throws ClassNotFoundException {
        if(this.currentDrink == null) throw new ClassNotFoundException("Please create drink first");
        if(this.currentDrink.getToppings() == null) this.currentDrink.setToppings(new ArrayList<>());

        this.currentDrink.getToppings().add(topping);
        return this;
    }

    /**
     * Check if the drink is valid. Return 0 if valid, otherwise return a negative code indicating the error.
     * -1: base drink is missing
     * -2: size is missing
     * -3: milk type is missing
     * @param drink the drink to check
     * @return 0 if valid, otherwise a negative code indicating the error
     */
    public static int invalidDrink(CustomDrink drink) {
        if (drink.getBaseDrink() == null) {
            return -1;
        }
        if (drink.getSize() == null) {
            return -2;
        }
        if (drink.getMilkType() == null) {
            return -3;
        }
        return 0;
    }
}