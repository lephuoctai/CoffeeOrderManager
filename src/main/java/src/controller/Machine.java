package src.controller;

import src.entity.*;
import src.entity.type.DrinkSize;
import src.entity.type.MilkType;
import src.entity.type.ToppingType;

import java.util.*;
import static src.controller.Bank.generateBill;
import static src.controller.DrinkOrder.invalidDrink;

public class Machine {
    private DrinkOrder currentOrder;
    private Double totalPrice;
    private String errorMessage;
    private String bill;

    private static final class InstanceHolder {
        private static final Machine INSTANCE = new Machine();
    }

    public static Machine getInstance() {
        return InstanceHolder.INSTANCE;
    }

    // Private constructor to prevent instantiation from outside the class
    private Machine() {
    }

    /**
     * Get the error message and clear it. If there is no error message, return null.
     * @return the error message, or null if there is no error message
     */
    public String getErrorMessage() {
        String t = errorMessage;
        errorMessage = null;
        return "[!] " + t;
    }

    public DrinkOrder getCurrentOrder() {
        if(currentOrder == null){
            errorMessage = "Please create drink first";
            return null;
        }
        return currentOrder;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public String getBill() {
        return bill;
    }

    /**
     * Create a new drink. If there is already a current drink, clear it and start a new one.
     * @return the machine instance
     */
    public DrinkOrder createOrder() {
        if(this.currentOrder != null){
            clearOrder();
        }
        this.currentOrder = new DrinkOrder();
        this.currentOrder.createDrink();
        return currentOrder;
    }

    public static boolean confirmOrder() {
        Machine machine = getInstance();
        if (machine.currentOrder == null) {
            machine.errorMessage = "No current order to confirm";
            return false;
        }
        CustomDrink currentDrink = machine.currentOrder.getCurrentDrink();
        if (invalidDrink(currentDrink) != 0) {
            machine.errorMessage = "Current drink is invalid, cannot confirm order";
            return false;
        }
        Bank.getInstance().addBalance(currentDrink.getPrice());
        machine.clearOrder();
        return true;
    }

    public boolean checkout() {
        CustomDrink currentDrink = currentOrder.getCurrentDrink();
        switch (invalidDrink(currentDrink)) {
            case -1:
                this.errorMessage = "Base drink is missing";
                break;
            case -2:
                this.errorMessage = "Size is missing";
                break;
            case -3:
                this.errorMessage = "Milk type is missing";
                break;
            case 0: {
                this.totalPrice = currentDrink.getPrice();
                this.bill = generateBill(currentDrink);
                return true;
            }
            default:
                errorMessage = null;
        }
        return false;
    }

    private void clearOrder() {
        this.currentOrder = null;
        this.totalPrice = null;
        this.errorMessage = null;
        this.bill = null;
    }

    //
    // API Helper methods
    //

    public static int inputId(Scanner scanner, String message, int max) {
        while (true) {
            try {
                System.out.print(message);
                int id = scanner.nextInt();
                if (id < 1 || id > max) throw new IllegalArgumentException();

                return id;
            }
            catch (Exception e) {
                System.out.println("[!] Invalid choice.");
                scanner.nextLine();
            }
        }
    }
    public static int inputToppingId(Scanner scanner, int max) {
        while (true) {
            try {
                int id = scanner.nextInt();
                if (id == 0) return 0;
                if (id < 1 || id > max) throw new IllegalArgumentException();

                return id;
            }
            catch (Exception e) {
                System.out.println("[!] Invalid topping id.");
                scanner.nextLine();
            }
        }
    }

    public static List<CoreDrink> getCoreDrinks() {
        return List.of(
                new Cappuccino(),
                new Latte(),
                new Espresso()
        );
    }

    public static List<DrinkSize> getSizes() {
        return Arrays.asList(DrinkSize.values());
    }

    public static List<MilkType> getMilkTypes() {
        return Arrays.asList(MilkType.values());
    }

    public static List<ToppingType> getToppings() {
        return Arrays.asList(ToppingType.values());
    }
}
